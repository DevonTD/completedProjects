/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superHero.model;

import java.util.Objects;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 *
 * @author devon
 */
public class Power {

    private int id;

    
    @Size(max = 30, message = "Name must be less than 30 characters")
    @Pattern(regexp = "^[a-zA-Z'-,.\\s]+$", message = "Please enter a valid name for the Super Power.")
    private String name;

  
    @Size(max = 50, message = "Description must be less than 50 characters")
    @Pattern(regexp = "^[a-zA-Z'!,.\\s]+$", message = "Please enter a valid descritpion for the Super Power.")
    private String description;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 29 * hash + this.id;
        hash = 29 * hash + Objects.hashCode(this.name);
        hash = 29 * hash + Objects.hashCode(this.description);
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
        final Power other = (Power) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.description, other.description)) {
            return false;
        }
        return true;
    }

}
