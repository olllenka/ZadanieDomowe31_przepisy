package com.example.cookbook.recipe;

import com.example.cookbook.recipeIngredient.RecipeIngredient;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RecipeService {

    private RecipeRepository recipeRepository;

    public RecipeService(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    public List<Recipe> find4MostPopular(){
        return recipeRepository.findTop4ByOrderByLikeCounterDescTitleAsc();
    }

    public List<Recipe> findAllSorted(String sort) {
        if("category".equals(sort)){
            return recipeRepository.findAllOrderByCategoryName();
        }
        else if("difficulty".equals(sort)){
            return recipeRepository.findAllOrderByDifficultyName();
        }
        else if("diet".equals(sort)){
            return recipeRepository.findAllOrderByDietName();
        }
        else {
            Sort sortBy = Sort.by(sort);
            return recipeRepository.findAll(sortBy);
        }
    }

    public List<Recipe> findAllFiltered(RecipeFilters recipeFilters) {
        return recipeRepository.findAllFiltered(recipeFilters.getTitle(), recipeFilters.getCategory(), recipeFilters.getDiet(), recipeFilters.getDifficulty());
    }

    public List<Recipe> findAllRecipes() {
        return recipeRepository.findAll();
    }

    public void updateRecipe(Recipe recipe) {
        recipeRepository.updateRecipeFull(recipe.getId(), recipe.getTitle(), recipe.getCategory(), recipe.getDiet(), recipe.getPreparationTimeInMinutes(), recipe.getDifficulty(), recipe.getDescription());
    }

    public Optional<Recipe> findById(Long id) {
        return recipeRepository.findById(id);
    }

    public void removeRecipe(Long id) {
        recipeRepository.deleteById(id);
    }

    public void saveRecipe(Recipe recipe) {
        recipeRepository.save(recipe);
    }

    public Recipe prepareRecipeWithTenIngredients() {
        int numberOfNewIngredients = 10;
        Recipe recipe = new Recipe();
        List<RecipeIngredient> recipeIngredientList = addNewRecipeIngredients(numberOfNewIngredients);
        recipe.setIngredients(recipeIngredientList);
        return recipe;
    }

    public void updateNumberOfLikes(Long id, int newLikeCounter) {
        recipeRepository.updateNumberOfLikesInRecipe(id, newLikeCounter);
    }

    public void addFiveEmptyRecipeIngredients(Recipe recipe) {
        int numberOfNewIngredients = 5;
        List<RecipeIngredient> recipeIngredientsList = recipe.getIngredients();
        List<RecipeIngredient> emptyIngredients = addNewRecipeIngredients(numberOfNewIngredients);
        recipeIngredientsList.addAll(emptyIngredients);
        recipe.setIngredients(recipeIngredientsList);
    }

    private List<RecipeIngredient> addNewRecipeIngredients(int numberOfIngredients) {
        List<RecipeIngredient> emptyIngredients = new ArrayList<>();
        for(int i=0; i<numberOfIngredients; i++){
            emptyIngredients.add(new RecipeIngredient());
        }
        return emptyIngredients;
    }
}
