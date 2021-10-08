/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superHero.model;

import java.util.List;
import java.util.Objects;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 *
 * @author devon
 */
public class Organization {

    private int id;

    @Size(max = 25, message = "Name must be less than 25 characters")
    @Pattern(regexp = "^[a-zA-Z,.\\s]+$", message = "Please enter a valid name for the Organization.")
    String name;

    @Size(max = 250, message = "Description must be less than 250 characters")
    @Pattern(regexp = "^[a-zA-Z0-9!',.\\s]+$", message = "Please enter a valid descritpion for the Organization.")
    String description;

    @Size(max = 10, min = 10, message = "Phone numbers must be 10 digits long. ex: 1234567890")
    @Pattern(regexp = "^[0-9]+$", message = "Please enter a valid phone number.")
    String phone;

    @NotBlank(message = "Email cannot be empty")
    @Size(max = 50, message = "Email addresses must be fewer than 50 characters.")
    @Email(message = "Please provide a valid email address.")
    String email;

    boolean hero;

    @NotNull(message = "Please add one or more super humans")
    @Size(min = 1, message = "Please add a minimum of one or more super humans")
    List<SuperHuman> superHumans;

    Location location;

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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isHero() {
        return hero;
    }

    public void setHero(boolean hero) {
        this.hero = hero;
    }

    public List<SuperHuman> getSuperHumans() {
        return superHumans;
    }

    public void setSuperHumans(List<SuperHuman> superHumans) {
        this.superHumans = superHumans;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 71 * hash + this.id;
        hash = 71 * hash + Objects.hashCode(this.name);
        hash = 71 * hash + Objects.hashCode(this.description);
        hash = 71 * hash + Objects.hashCode(this.phone);
        hash = 71 * hash + Objects.hashCode(this.email);
        hash = 71 * hash + (this.hero ? 1 : 0);
        hash = 71 * hash + Objects.hashCode(this.superHumans);
        hash = 71 * hash + Objects.hashCode(this.location);
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
        final Organization other = (Organization) obj;
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
        if (!Objects.equals(this.phone, other.phone)) {
            return false;
        }
        if (!Objects.equals(this.email, other.email)) {
            return false;
        }
        if (!Objects.equals(this.superHumans, other.superHumans)) {
            return false;
        }
        if (!Objects.equals(this.location, other.location)) {
            return false;
        }
        return true;
    }

}
