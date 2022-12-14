package com.nalstudio.springjms.api.controller;

import com.nalstudio.springjms.data.IngredientRepository;
import com.nalstudio.springjms.data.TacoRepository;
import com.nalstudio.springjms.data.UserRepository;
import com.nalstudio.springjms.domain.Taco;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(path = "/design", produces = "application/json")
@CrossOrigin(origins = "*")
public class DesignTacoController {

    private final IngredientRepository ingredientRepository;
    private final TacoRepository tacoRepository;
    private final UserRepository userRepository;

    @Autowired
    public DesignTacoController(IngredientRepository ingredientRepository,
                                TacoRepository tacoRepository,
                                UserRepository userRepository) {
        this.ingredientRepository = ingredientRepository;
        this.tacoRepository = tacoRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/recent")
    public Iterable<Taco> recentTacos() {
        PageRequest page = PageRequest.of(
                0, 12, Sort.by("createdAt").descending()
        );
        return tacoRepository.findAll(page).getContent();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Taco> tacoById(@PathVariable("id") Long id) {
        Optional<Taco> optTaco = tacoRepository.findById(id);
        if(optTaco.isPresent()) {
            return new ResponseEntity<>(optTaco.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @PostMapping(consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)//표시된 메소드가 성공적으로 완료시 return status
    public Taco postTaco(@RequestBody Taco taco) {
        return tacoRepository.save(taco);
    }

}
