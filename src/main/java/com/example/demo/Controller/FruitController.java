package com.example.demo.Controller;

import com.example.demo.Fruit;
import com.example.demo.Repository.FruitRepository;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.Map;

@RestController
public class FruitController {

    private final FruitRepository repository;

    public FruitController(FruitRepository repository) {
        this.repository = repository;
    }

    //CREATE
    @PostMapping("/fruit")
    public Fruit addFruitToDB(@RequestBody Fruit fruit){
//        this.repository.save(fruit);
//        return "Added " + fruit.getName() + " as id: " + fruit.getId();
        return this.repository.save(fruit);
    }
    //READ
    @GetMapping("fruit")
    public Iterable<Fruit> getAllFruitsFromDB(){
        return this.repository.findAll();
    }

    //UPDATE (reading new values from the request body and updating only those
    @PatchMapping("/fruit/{id}")
    public Fruit updateFruitInDB(@PathVariable Long id, @RequestBody Map<String, Object> newFruit){
        Fruit oldFruit = this.repository.findById(id).get();
        StringBuilder sb = new StringBuilder();
        //iterate over new fruit for each value that you want to be able to update
        newFruit.forEach((key, value) -> {
            switch (key) {
                case "ripe":
                    oldFruit.setRipe((boolean) value);
                    //sb.append("updated ripe\n");
                    break;
                case "color":
                    oldFruit.setColor((String) value);
                    //sb.append("updated color\n");
                    break;
                case "name":
                    oldFruit.setName((String) value);
                    //sb.append("updated name\n");
                    break;
                case "season":
                    //below will make the value a String to convert it to an integer that can be sent to the method that will return the text of the season
                    oldFruit.setSeason(returnSeasonName(Integer.getInteger(value.toString())));
                    //sb.append("updated season\n");
                    break;
            }
        });

        sb.append("\nupdated item id: " + oldFruit.getId());
        return this.repository.save(oldFruit);
    }
    //DELETE
    @DeleteMapping("/fruit/delete/{id}")
    public String deleteFruitFromDB(@PathVariable Long id)
    {
        Fruit tempFruit = new Fruit();
        StringBuilder sb = new StringBuilder();
        try {
            tempFruit = repository.findById(id).get();
            this.repository.deleteById(id);
            sb.append("deleted item: " + id + " (" + tempFruit.getName() + ")");
        }
        catch(Exception e)
        {sb.append("something went wrong, could not find item: \n" + id.toString() + e);}
        return sb.toString();
    }
    public Fruit.Season returnSeasonName (int seasonId){
        Fruit.Season mySeason= Fruit.Season.FALL;
        switch (seasonId){
            case 0: mySeason = Fruit.Season.WINTER; return mySeason;
            case 1: mySeason = Fruit.Season.SPRING; return mySeason;
            case 2: mySeason = Fruit.Season.SUMMER; return mySeason;
            case 3: mySeason = Fruit.Season.FALL; return mySeason;
        }return mySeason;
    }
    @GetMapping("/fruits/{name}")
    public Fruit getFruitByName(@PathVariable String name){
        return this.repository.findByName(name);
    }
}
