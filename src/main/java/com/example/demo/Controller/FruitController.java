package com.example.demo.Controller;

import com.example.demo.Fruit;
import com.example.demo.FruitRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FruitController {

    private final FruitRepository repository;

    public FruitController(FruitRepository repository) {
        this.repository = repository;
    }
    //CREATE
    @PostMapping("/fruits")
    public void addFruitToDB(@RequestBody Fruit fruit){
        this.repository.save(fruit);
    }
    //READ
    @GetMapping("fruits")
    public Iterable<Fruit> getAllFruitsFromDB(){
        return this.repository.findAll();
    }
    //UPDATE


}
