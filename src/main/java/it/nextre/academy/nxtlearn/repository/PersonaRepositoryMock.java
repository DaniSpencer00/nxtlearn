package it.nextre.academy.nxtlearn.repository;

import it.nextre.academy.nxtlearn.model.Persona;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class PersonaRepositoryMock {
    private List<Persona> db = new ArrayList<>();

    {
        db.add(new Persona(1,"Mario","Red"));
        db.add(new Persona(2,"Michele","Bossi"));
        db.add(new Persona(3,"Michela","Manelli"));
        db.add(new Persona(4,"Gino","Brunetta"));
        db.add(new Persona(5,"Maria","Vongola"));
        db.add(new Persona(6,"Gina","Branzino"));
    }

    public Persona save(Persona persona){
        if (persona.getId()!=null && persona.getId()>0){
            if (db.stream()
                    .map(per->per.getId())
                    .collect(Collectors.toList())
                    .contains(persona.getId())) {
                //posso aggiornare
                Persona tmp = db.stream().filter(per->per.getId().equals(persona.getId())).findFirst().get();
                //aggiorno TUTTI i campi dell'oggetto
                tmp.setNome(persona.getNome());
                tmp.setCognome(persona.getCognome());
                return tmp.clone();
            }else{
                //la persona non esiste, MA volevo aggiornarla
                return null;
            }
        }else{
            //devo inserire
            //calcolare il nuovo id
            persona.setId(
                    1+db.stream()
                    .map(per->per.getId())
                    .max(Integer::compareTo)
                    .orElse(0)
            );
            db.add(persona);
            return persona.clone();
        }
    }

    public List<Persona> findAll(){
        List<Persona> tmp = new ArrayList<>();
        for(Persona p:db){
            tmp.add(p.clone());
        }
        return tmp;
    }

    public Optional<Persona> findById(Integer id) {
        Persona tmp = db.stream().filter(p->p.getId().equals(id)).findFirst().orElse(null);
        if (tmp!=null)
        return Optional.of(tmp.clone());
        else
        return Optional.empty();
    }

    public void deleteById(Integer id) {
        Persona tmp = findById(id).orElse(null);
        if (tmp!=null){
            if(!db.remove(tmp)){
                throw new RuntimeException();
            }
        }
    }
}//end class