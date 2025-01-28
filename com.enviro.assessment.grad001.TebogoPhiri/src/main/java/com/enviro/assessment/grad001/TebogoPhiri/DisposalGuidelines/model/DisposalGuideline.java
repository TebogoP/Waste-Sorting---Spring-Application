package com.enviro.assessment.grad001.TebogoPhiri.DisposalGuidelines.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import org.springframework.data.annotation.Id;

public class DisposalGuideline  {
    public DisposalGuideline () {
    }
    public DisposalGuideline (Integer id, String wasteType,String guideline) {
        setId(id);
        setGuideline(guideline);
        setWasteType(wasteType);
    }
    @Id
    @NotNull(message = "ID cannot be null")
    @Positive(message = "ID should not be negative")
    private Integer id;


    @NotBlank(message = "Guideline cannot be blank")
    private String guideline;

    @NotBlank(message = "wasteType cannot be blank")
    @Size(max = 255, message = "wasteType cannot exceed 255 characters")
    private String wasteType;

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getGuideline() {
        return guideline;
    }
    public void setGuideline(String guideline) {
        this.guideline = guideline;
    }
    public String getWasteType() {
        return wasteType;
    }
    public void setWasteType(String wasteType) {
        this.wasteType = wasteType;
    }

    @Override
    public String toString() {
        return "{" +
                "\"id\": " + id + ", " +
                "\"wasteType\": \"" + wasteType + "\"," +
                "\"guideline\": \"" + guideline + "\"" +
                "}";
    }
}
