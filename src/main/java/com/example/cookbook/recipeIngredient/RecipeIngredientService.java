package com.example.cookbook.recipeIngredient;

import com.example.cookbook.recipe.Recipe;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RecipeIngredientService {

    RecipeIngredientRepository recipeIngredientRepository;

    public RecipeIngredientService(RecipeIngredientRepository recipeIngredientRepository) {
        this.recipeIngredientRepository = recipeIngredientRepository;
    }

    public void updateRecipeIngredientsAfterRecipeSave(Recipe recipe) {

        recipeIngredientRepository.deleteEmptyRecipeIngredients();

        List<RecipeIngredient> filledIngredients = findFilledRecipeIngredients(recipe.getIngredients());

        List<Long> filledRecipeIngredientsIds = findFilledRecipeIngredientsIds(filledIngredients);

        recipeIngredientRepository.updateRecipeIdInRecipeIngredients(filledRecipeIngredientsIds, recipe);
    }

    public void updateRecipeIngredientsAfterRecipeEdition(Recipe recipe) {

        List<RecipeIngredient> filledIngredients = findFilledRecipeIngredients(recipe.getIngredients());
        List<RecipeIngredient> newIngredients = new ArrayList<>();

        for(int i = 0; i<filledIngredients.size(); i++){
            RecipeIngredient ingredientToUpdatOrSave = filledIngredients.get(i);
            if(isNewIngredient(ingredientToUpdatOrSave.getId())){
                newIngredients.add(ingredientToUpdatOrSave);
            }
            else {
                updateExistingIngredient(ingredientToUpdatOrSave, recipe);
            }
        }
        saveNewIngredientsInRecipe(newIngredients, recipe);
    }

    private void saveNewIngredientsInRecipe(List<RecipeIngredient> newIngredients, Recipe recipe) {
        recipeIngredientRepository.saveAll(newIngredients);
        List<Long> filledRecipeIngredientsIds = findFilledRecipeIngredientsIds(newIngredients);
        recipeIngredientRepository.updateRecipeIdInRecipeIngredients(filledRecipeIngredientsIds, recipe);
    }

    private void updateExistingIngredient(RecipeIngredient ingredientToUpdate, Recipe recipe) {
        recipeIngredientRepository.updateRecipeIngredientFull(ingredientToUpdate.getId(), recipe, ingredientToUpdate.getName(), ingredientToUpdate.getUnit(), ingredientToUpdate.getAmount());
    }

    private boolean isNewIngredient(Long ingredientId){
        if(recipeIngredientRepository.countAllByIdEquals(ingredientId)==1)
            return false;
        else return true;
    }

    private List<Long> findFilledRecipeIngredientsIds(List<RecipeIngredient> filledIngredients) {
        return filledIngredients.stream()
                .map(RecipeIngredient::getId)
                .collect(Collectors.toList());
    }

    private List<RecipeIngredient> findFilledRecipeIngredients(List<RecipeIngredient> ingredients) {
         return ingredients.stream()
                .filter(recipeIngredient -> recipeIngredient.getAmount()>0)
                .filter(recipeIngredient -> !recipeIngredient.getName().isEmpty())
                .filter(recipeIngredient -> (recipeIngredient.getUnit()) != null)
                .collect(Collectors.toList());
    }
}
