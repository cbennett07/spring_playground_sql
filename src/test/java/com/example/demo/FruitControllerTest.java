package com.example.demo;

import com.example.demo.Repository.FruitRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.shadow.com.univocity.parsers.annotations.Nested;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
public class FruitControllerTest {

    @Autowired
    MockMvc mvc;

    @Autowired
    FruitRepository repository;

        @Test
        @Transactional
        @Rollback
        public void testControllerCreateFruit() throws Exception {
            MockHttpServletRequestBuilder request = post("/fruit")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"name\": \"banana\",\n\"color\": \"yellow\"\n}");

            this.mvc.perform(request)
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.name", is("banana") ));
        }

        @Test
        @Transactional
        @Rollback
        public void testControllerListFruitsWithGivenName() throws Exception {
            Fruit fruit = new Fruit();
            fruit.setName("Dragon Fruit");
            repository.save(fruit);
            Fruit fruit1 = new Fruit();
            fruit1.setName("Dragon Fruit");
            repository.save(fruit1);

            MockHttpServletRequestBuilder request = get("/fruit")
                    .contentType(MediaType.APPLICATION_JSON);

            this.mvc.perform(request)
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$[0].id", equalTo(fruit.getId().intValue()) ))
                    .andExpect(jsonPath("$[0].name", is("Dragon Fruit")))
                    .andExpect(jsonPath("$[1].name", is("Dragon Fruit")))
                    //.andExpect(jsonPath("$[2].name", is("Dragon Fruit")))
            ;
        }

    @Test
    @Transactional
    @Rollback
    public void testControllerUpdateFruitWithId() throws Exception {
            //create and save lemon in repo
            Fruit fruit = new Fruit();
            fruit.setName("lemon");
            fruit.setColor("yellow");
            repository.save(fruit);
            //pull id from repo
            Long newId = fruit.getId();
        //create
        MockHttpServletRequestBuilder request = patch("/fruit/" + newId)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\": \"banana\",\n\"color\": \"yellow\"\n}");

        this.mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo(fruit.getId().intValue()) ))
                .andExpect(jsonPath("$.name", is("banana")))
        ;
    }
}
