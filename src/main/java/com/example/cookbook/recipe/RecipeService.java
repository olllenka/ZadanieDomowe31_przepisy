package com.example.cookbook.recipe;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecipeService {

    private RecipeRepository recipeRepository;

    public RecipeService(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    public List<Recipe> find8MostPopular(){
        return recipeRepository.findTop8ByOrderByLikeCounterDesc();
    }

    public List<Recipe> findAllSorted(String sort) {
        if("category".equals(sort)){
            return recipeRepository.recipesOrderByCategoryName();
        }
        else if("difficulty".equals(sort)){
            return recipeRepository.recipesOrderByDifficultyName();
        }
        else if("diet".equals(sort)){
            return recipeRepository.recipesOrderByDietName();
        }
        else {
            Sort sortBy = Sort.by(sort);
            return recipeRepository.findAll(sortBy);
        }
    }

    public List<Recipe> findAllForFilters(RecipeFilters recipeFilters) {
        //return recipeRepository.findByTitleContainsIgnoreCaseAndCategoriesContainsIgnoreCase(recipeFilters.getTitle(), recipeFilters.getCategory());
        //return recipeRepository.findByTitleContainsIgnoreCaseAndCategoryContainsIgnoreCaseAndDifficultyContainsIgnoreCaseAndDietContainsIgnoreCaseAnd(recipeFilters.getTitle(), recipeFilters.getCategory(), recipeFilters.getDifficulty(), recipeFilters.getDiet());
        return recipeRepository.findByTitleContainsIgnoreCase(recipeFilters.getTitle());
    }

    public List<Recipe> findAllRecipes() {
        return recipeRepository.findAll();
    }
/*
    public Page<Recipe> findAllForFiltersAndSort(RecipeFilters recipeFilters, Pageable pageable) {

    }

 */
}
