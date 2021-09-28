package com.example.demo.Repository;

import com.example.demo.Fruit;
import org.springframework.data.repository.CrudRepository;

public interface FruitRepository extends CrudRepository<Fruit, Long> {

    Fruit findByName(String name);
}
