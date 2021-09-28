package com.example.demo;

import javax.persistence.*;

@Entity
@Table(name="fruit")
public class Fruit {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String color;
    private boolean isRipe;
    @Enumerated(EnumType.STRING)
    private Season season;

    public Season getSeason() {
        return season;
    }

    public void setSeason(Season season) {
        this.season = season;
    }

    public enum Season{WINTER, SPRING, SUMMER, FALL}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public boolean isRipe() {
        return isRipe;
    }

    public void setRipe(boolean ripe) {
        isRipe = ripe;
    }
}
