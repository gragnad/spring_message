package com.nalstudio.springjms.api.controller;

import com.nalstudio.springjms.data.IngredientRepository;
import com.nalstudio.springjms.domain.Ingredient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.EmptyStackException;
import java.util.Optional;

@RestController
@RequestMapping(path = "/ingredients", produces = "application/json")
@CrossOrigin(origins = "*")
public class IngredientController {

    private IngredientRepository ingredientRepository;

    @Autowired
    public IngredientController(IngredientRepository repository) {
        this.ingredientRepository = repository;
    }

    @GetMapping
    public Iterable<Ingredient> allIngredients() {
        return ingredientRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ingredient> getIngredient(@PathVariable("id") String id) {
        Optional<Ingredient> ingredient = ingredientRepository.findById(id);
        if(ingredient.isPresent()) {
            return new ResponseEntity<>(ingredient.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @PostMapping(consumes = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public Ingredient createIngredient(@RequestBody Ingredient ingredient) {
        return ingredientRepository.save(ingredient);
    }

    @PutMapping(path = "/{id}", consumes = "application/json")
    public Ingredient putIngredient(@RequestBody Ingredient ingredient) {
        //update pk id / save is pk key null insert other update
        return ingredientRepository.save(ingredient);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteIngredient(@PathVariable("id") String id) {
        try {
            ingredientRepository.deleteById(id);
        } catch (EmptyStackException e) {
            e.printStackTrace();
        }
    }
}
