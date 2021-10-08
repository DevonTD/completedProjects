/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superHero.model;

import java.util.List;
import java.util.Objects;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 *
 * @author devon
 */
public class SuperHuman {

    private int id;

    @Size(max = 25, message = "Name must be less than 25 characters")
    @Pattern(regexp = "^[a-zA-Z0-9!,.\\s]+$", message = "Please enter a valid name for the Super Human.")
    String name;

    @NotBlank(message = "Description must not be blank")
    @Size(max = 100, message = "Description must be less than 100 characters")
    String description;

    boolean hero;

    @NotNull(message = "Please add one or more powers")
    @Size(min = 1, message = "Please add a minimum of one or more powers")
    List<Power> powers;

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

    public boolean isHero() {
        return hero;
    }

    public void setHero(boolean hero) {
        this.hero = hero;
    }

    public List<Power> getPowers() {
        return powers;
    }

    public void setPowers(List<Power> powers) {
        this.powers = powers;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 17 * hash + this.id;
        hash = 17 * hash + Objects.hashCode(this.name);
        hash = 17 * hash + Objects.hashCode(this.description);
        hash = 17 * hash + (this.hero ? 1 : 0);
        hash = 17 * hash + Objects.hashCode(this.powers);
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
        final SuperHuman other = (SuperHuman) obj;
        if (this.id != other.id) {
            return false;
        }
        if (this.hero != other.hero) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.description, other.description)) {
            return false;
        }
        if (!Objects.equals(this.powers, other.powers)) {
            return false;
        }
        return true;
    }
}
