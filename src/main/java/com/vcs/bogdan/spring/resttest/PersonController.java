package com.vcs.bogdan.spring.resttest;


import com.vcs.bogdan.beans.Person;
import com.vcs.bogdan.service.db.PersonService;
import com.vcs.bogdan.ui.App;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/person")
public class PersonController {

    @Autowired
    private PersonService service = new PersonService();

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Person getPerson(@PathVariable("id") String id) {
        return service.get(id);
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public List<Person> getAll() {
        return service.getAll();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Void> addPerson(@PathVariable("id") String id, @RequestBody Person person) {

        service.add(person);
        return ResponseEntity.ok().build();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> remove(@PathVariable("id") String id) {

        service.remove(id);
        return ResponseEntity.ok().build();
    }

    @RequestMapping(value = "/search/name/{name}", method = RequestMethod.POST)
    public ResponseEntity<List<Person>> searchByName(@PathVariable("name") String nameSearch) {

        List<Person> result = service.getAll();
        if (result.isEmpty()) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } else {
            return ResponseEntity.accepted().body(result);
        }
        return null;
    }

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public List<Person> searchByName(@RequestBody Person person) {
        return service.getAll();
    }

}