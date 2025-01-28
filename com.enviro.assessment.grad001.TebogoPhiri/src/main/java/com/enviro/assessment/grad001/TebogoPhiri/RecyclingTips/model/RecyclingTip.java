package com.enviro.assessment.grad001.TebogoPhiri.RecyclingTips.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import org.springframework.data.annotation.Id;

public class RecyclingTip {
    public RecyclingTip() {
    }
    public RecyclingTip(Integer id, String tip, String category) {
        setId(id);
        setTip(tip);
        setCategory(category);
    }
    @Id
    @NotNull(message = "ID cannot be null")
    @Positive(message = "ID should not be negative")
    private Integer id;


    @NotBlank(message = "Tip cannot be blank")
    @Size(max = 100, message = "Tip cannot exceed 100 characters")
    private String tip;

    @NotBlank(message = "Category cannot be blank")
    @Size(max = 255, message = "Category cannot exceed 255 characters")
    private String category;

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getTip() {
        return tip;
    }
    public void setTip(String tip) {
        this.tip = tip;
    }
    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "{" +
                "\"id\": " + id + ", " +
                "\"tip\": \"" + tip + "\", " +
                "\"category\": \"" + category + "\"" +
                "}";
    }
}
