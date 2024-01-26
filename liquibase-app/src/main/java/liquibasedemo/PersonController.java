package liquibasedemo;

import com.data.entity.Person;
import com.data.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PersonController {

    @Autowired
    private PersonRepository repo;

    @GetMapping("/")
    public String greet() {
        return "Welcome to liquibase POC";
    }

    @GetMapping("/person/all")
    public ResponseEntity<List<Person>> fetchAll() {
        return ResponseEntity.ok(repo.findAll());
    }

}
