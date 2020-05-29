package com.example.cookbook.recipe;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {

    public List<Recipe> findTop8ByOrderByLikeCounterDesc();



    //public List<Recipe> findByTitleContainsIgnoreCaseAndCategoryContainsIgnoreCase(String title, String categoryName);

    public List<Recipe> findByCategoryContainsAndTitleContains(String category, String title, Sort sortBy);
    public List<Recipe> findByTitleContainsIgnoreCaseAndLikeCounter(String category, int counter);

    //public List<Recipe> findByTitleContainsIgnoreCaseAndCategoryContainsIgnoreCaseAndDifficultyContainsIgnoreCaseAndDietContainsIgnoreCaseAnd(String title, String category, String difficulty, String diet);

    List<Recipe> findByTitleContainsIgnoreCase(String title);

    public List<Recipe> findAll(Sort sortBy);



    //native query: SELECT * FROM recipe r JOIN category c ON r.category_id = c.id ORDER BY c.name;
    @Query("SELECT r FROM Recipe r JOIN Category c ORDER BY c.name")
    //@Query(value = "SELECT c FROM Recipe r, Category c WHERE r.category = c ORDER BY c.name")
    List<Recipe> recipesOrderByCategoryName();

    @Query("SELECT r FROM Recipe r JOIN Difficulty d ORDER BY d.name")
    List<Recipe> recipesOrderByDifficultyName();

    @Query("SELECT r FROM Recipe r JOIN Diet d ORDER BY d.name")
    List<Recipe> recipesOrderByDietName();
}
