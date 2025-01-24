package com.enviro.assessment.grad001.TebogoPhiri.model;

public class WasteCategory  {
    public WasteCategory() {
    }
    public WasteCategory(int id, String name, String description) {
        setId(id);
        setName(name);
        setDescription(description);
    }
    private int id;

    private String name;

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
    public String toString() {
        return "{" +
                "\"id\": " + id + ", " +
                "\"name\": \"" + name + "\", " +
                "\"description\": \"" + description + "\"" +
                "}";
    }
}
