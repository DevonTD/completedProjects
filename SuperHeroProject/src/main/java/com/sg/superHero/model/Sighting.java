/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superHero.model;


import java.time.LocalDate;
import java.util.Objects;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;


/**
 *
 * @author devon
 */
public class Sighting {

    private int id;

    
    SuperHuman superHuman;

    Location location;

    @PastOrPresent(message = "Date cannot be in the future")
    LocalDate date;

    @NotBlank(message = "Description must not be empty")
    @Size(max = 100, message = "Description must be less than 100 characters")
    String description;

    @NotBlank(message = "Name must not be empty")
    @Size(max = 25, message = "Name must be less than 25 characters")
    String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public SuperHuman getSuperHuman() {
        return superHuman;
    }

    public void setSuperHuman(SuperHuman superHuman) {
        this.superHuman = superHuman;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + this.id;
        hash = 71 * hash + Objects.hashCode(this.superHuman);
        hash = 71 * hash + Objects.hashCode(this.location);
        hash = 71 * hash + Objects.hashCode(this.date);
        hash = 71 * hash + Objects.hashCode(this.description);
        hash = 71 * hash + Objects.hashCode(this.name);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Sighting other = (Sighting) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.description, other.description)) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.superHuman, other.superHuman)) {
            return false;
        }
        if (!Objects.equals(this.location, other.location)) {
            return false;
        }
        if (!Objects.equals(this.date, other.date)) {
            return false;
        }
        return true;
    }
    
}
