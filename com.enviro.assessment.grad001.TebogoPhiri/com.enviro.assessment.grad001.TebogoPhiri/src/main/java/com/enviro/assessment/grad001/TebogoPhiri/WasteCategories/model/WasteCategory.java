package com.enviro.assessment.grad001.TebogoPhiri.WasteCategories.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public class WasteCategory  {
    public WasteCategory() {
    }
    public WasteCategory(Integer id, String name, String description) {
        setId(id);
        setName(name);
        setDescription(description);
    }
    @NotNull(message = "ID cannot be null")
    @Positive(message = "ID should not be negative")
    private Integer id;

    @NotBlank(message = "Name cannot be blank")
    @Size(max = 100, message = "Name cannot exceed 100 characters")
    private String name;

    @NotBlank(message = "Description cannot be blank")
    @Size(max = 255, message = "Description cannot exceed 255 characters")
    private String description;

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
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
    public String toString() {
        return "{" +
                "\"id\": " + id + ", " +
                "\"name\": \"" + name + "\", " +
                "\"description\": \"" + description + "\"" +
                "}";
    }
}
