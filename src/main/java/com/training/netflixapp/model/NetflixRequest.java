package com.training.netflixapp.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

public class NetflixRequest {
    @NotNull
    @NotBlank
    private String type;

    @NotNull
    @NotBlank
    private String title;

    @NotNull
    private LocalDate dateAdded;

    @NotNull
    @NotEmpty
    private List<String> listedIn;

    @NotNull
    @NotBlank
    private String country;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(LocalDate dateAdded) {
        this.dateAdded = dateAdded;
    }

    public List<String> getListedIn() {
        return listedIn;
    }

    public void setListedIn(List<String> listedIn) {
        this.listedIn = listedIn;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
