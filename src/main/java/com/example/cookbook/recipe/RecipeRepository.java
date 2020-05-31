package com.example.cookbook.recipe;

import com.example.cookbook.dictionary.category.Category;
import com.example.cookbook.dictionary.diet.Diet;
import com.example.cookbook.dictionary.difficulty.Difficulty;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {

    List<Recipe> findTop4ByOrderByLikeCounterDescTitleAsc();

    Optional<Recipe> findById(Long id);

    //filering query
    @Query("SELECT r FROM Recipe r " +
            "WHERE LOWER(r.title) LIKE LOWER(CONCAT('%', :title, '%'))" +
            " AND r.category IN (SELECT c.id FROM Category c WHERE LOWER(c.name) LIKE LOWER(CONCAT('%', :category, '%')))" +
            " AND r.diet IN (SELECT d.id FROM Diet d WHERE LOWER(d.name) LIKE LOWER(CONCAT('%', :diet, '%')))" +
            " AND r.difficulty IN (SELECT di.id FROM Difficulty di WHERE LOWER(di.name) LIKE LOWER(CONCAT('%', :difficulty, '%')))")
    List<Recipe> findAllFiltered(@Param("title") String title, @Param("category") String category, @Param("diet") String diet, @Param("difficulty") String difficulty);

    //sorting queries
    List<Recipe> findAll(Sort sortBy);

    @Query(value="SELECT * FROM recipe r JOIN category c ON r.category_id = c.id ORDER BY c.name", nativeQuery = true)
    List<Recipe> findAllOrderByCategoryName();

    @Query(value="SELECT * FROM recipe r JOIN difficulty d ON r.difficulty_id = d.id ORDER BY d.name", nativeQuery = true)
    List<Recipe> findAllOrderByDifficultyName();

    @Query(value="SELECT * FROM recipe r JOIN category c ON r.category_id = c.id ORDER BY c.name", nativeQuery = true)
    List<Recipe> findAllOrderByDietName();

    @Transactional
    @Modifying
    @Query("UPDATE Recipe r SET r.title = :title, r.category = :category, r.diet = :diet, r.preparationTimeInMinutes = :time, r.difficulty = :difficulty, r.description = :description WHERE r.id = :id")
    int updateRecipeFull(@Param("id") Long id, @Param("title") String title, @Param("category") Category category, @Param("diet") Diet diet, @Param("time") int time, @Param("difficulty") Difficulty difficulty, @Param("description") String description);

    @Transactional
    @Modifying
    void deleteById(Long id);

    @Transactional
    @Modifying
    @Query("UPDATE Recipe r SET r.likeCounter = :likeCounter WHERE r.id = :id")
    void updateNumberOfLikesInRecipe(@Param("id") Long id, @Param("likeCounter") int likeCounter);


/*
    //native query: SELECT * FROM recipe r JOIN category c ON r.category_id = c.id ORDER BY c.name;
    @Query("SELECT r FROM Recipe r JOIN Category c ORDER BY c.name")
    //@Query(value = "SELECT c FROM Recipe r, Category c WHERE r.category = c ORDER BY c.name")
    List<Recipe> recipesOrderByCategoryName();

    @Query("SELECT r FROM Recipe r JOIN Difficulty d ORDER BY d.name")
    List<Recipe> recipesOrderByDifficultyName();

    @Query("SELECT r FROM Recipe r JOIN Diet d ORDER BY d.name")
    List<Recipe> recipesOrderByDietName();

     public List<Recipe> findByCategoryContainsAndTitleContains(String category, String title, Sort sortBy);
    public List<Recipe> findByTitleContainsIgnoreCaseAndLikeCounter(String category, int counter);

    List<Recipe> findByTitleContainsIgnoreCase(String title);

 */
}
