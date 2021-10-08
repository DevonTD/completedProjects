/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superHero.model;

import java.math.BigDecimal;
import java.util.Objects;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 *
 * @author devon
 */
public class Location {

    private int id;

    @Size(max = 25, message = "Name must be less than 25 characters")
    @Pattern(regexp = "^[a-zA-Z,.\\s]+$", message = "Please enter a valid name for the Location.")
    String name;


    @Size(max = 250, message = "Description must be less than 250 characters")
    @Pattern(regexp = "^[a-zA-Z0-9!',.\\s]+$", message = "Please enter a valid descritpion for the Location.")
    String description;


    @Pattern(regexp = "^[a-zA-Z,.\\s]+$", message = "Please enter a valid  name for the street.")
    @Size(max = 30, message = "Street name must be less than 30 characters")
    String streetName;

   
    @Pattern(regexp = "^[0-9]*$", message = "Please enter a valid street number.")
    @Size(max = 5, message = "Street number must be less than 5 characters")
    String streetNumber;

 
    @Pattern(regexp = "^[a-zA-Z,.\\s]+$", message = "Please enter a valid name for the City.")
    @Size(max = 45, message = "City must be less than 45 characters")
    String city;


    @Pattern(regexp = "^[A-Z]+$", message = "Please enter a valid name for the state.")
    @Size(max = 2, min = 2, message = "State must be 2 charcters long")
    String state;


    @Pattern(regexp = "^[0-9]*$", message = "Please enter a valid zip code.")
    @Size(max = 5, min = 5, message = "Zip must be 5 characters long")
    String zip;

    @DecimalMax(value = "180.00", message = "Longitude can only be 180 degrees or less.")
    @DecimalMin(value = "-180.00", message = "Longitude can only be -180 degrees or more.")
    @Digits(integer = 9, fraction = 6, message = "Please enter a valid value for Longitude.")
    @NotNull(message = "Please enter a valid value for longitude.")
    BigDecimal longitude;

    @DecimalMax(value = "90.00", message = "Latitude can only be 90 degrees or less.")
    @DecimalMin(value = "-90.00", message = "Latitude can only be -90 degrees or more.")
    @Digits(integer = 8, fraction = 6, message = "Please enter a valid value for Latitude.")
    @NotNull(message = "Please enter a valid value for latitude.")
    BigDecimal latitude;

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

    public String getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(String streetNumber) {
        this.streetNumber = streetNumber;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + this.id;
        hash = 83 * hash + Objects.hashCode(this.name);
        hash = 83 * hash + Objects.hashCode(this.description);
        hash = 83 * hash + Objects.hashCode(this.streetNumber);
        hash = 83 * hash + Objects.hashCode(this.streetName);
        hash = 83 * hash + Objects.hashCode(this.city);
        hash = 83 * hash + Objects.hashCode(this.state);
        hash = 83 * hash + Objects.hashCode(this.zip);
        hash = 83 * hash + Objects.hashCode(this.longitude);
        hash = 83 * hash + Objects.hashCode(this.latitude);
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
        final Location other = (Location) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.description, other.description)) {
            return false;
        }
        if (!Objects.equals(this.streetNumber, other.streetNumber)) {
            return false;
        }
        if (!Objects.equals(this.streetName, other.streetName)) {
            return false;
        }
        if (!Objects.equals(this.city, other.city)) {
            return false;
        }
        if (!Objects.equals(this.state, other.state)) {
            return false;
        }
        if (!Objects.equals(this.zip, other.zip)) {
            return false;
        }
        if (!Objects.equals(this.longitude, other.longitude)) {
            return false;
        }
        if (!Objects.equals(this.latitude, other.latitude)) {
            return false;
        }
        return true;
    }

}
