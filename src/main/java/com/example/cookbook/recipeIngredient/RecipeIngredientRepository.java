package com.example.cookbook.recipeIngredient;

import com.example.cookbook.dictionary.unit.Unit;
import com.example.cookbook.recipe.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface RecipeIngredientRepository extends JpaRepository<RecipeIngredient, Long> {

    @Transactional
    @Modifying
    @Query("UPDATE RecipeIngredient ri SET ri.recipe = :recipe WHERE ri.id = :id")
    int updateById(@Param("id") Long id, @Param("recipe") Recipe recipe);

    @Transactional
    @Modifying
    @Query("UPDATE RecipeIngredient ri SET ri.recipe = :recipe WHERE ri.id IN :ids")
    void updateRecipeIdInRecipeIngredients(@Param("ids") List<Long> recipeIngredients, @Param("recipe") Recipe recipe);

    @Transactional
    @Modifying
    @Query("UPDATE RecipeIngredient ri SET ri.recipe = :recipe, ri.name = :name, ri.unit = :unit, ri.amount = :amount WHERE ri.id = :id")
    void updateRecipeIngredientFull(@Param("id") Long id, @Param("recipe") Recipe recipe, @Param("name") String name, @Param("unit") Unit unit, @Param("amount") double amount);

    @Transactional
    @Modifying
    @Query("DELETE FROM RecipeIngredient ri WHERE ri.amount <= 0 OR ri.name = '' OR ri.unit IS NULL")
    void deleteEmptyRecipeIngredients();

    Long countAllByIdEquals(Long id);
}
