package com.example.cookbook.recipe;


import com.example.cookbook.dictionary.Category;

import java.util.List;

public class RecipeFilters {

    private String title;
    private String category;
    private String diet;
    private String difficulty;


    public RecipeFilters() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDiet() {
        return diet;
    }

    public void setDiet(String diet) {
        this.diet = diet;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }
}
