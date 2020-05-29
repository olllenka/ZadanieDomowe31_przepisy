package com.example.cookbook.recipe;

import com.example.cookbook.dictionary.Category;
import com.example.cookbook.dictionary.CategoryRepository;
import com.example.cookbook.dictionary.UnitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class RecipeController {

    private RecipeRepository recipeRepository;
    private UnitRepository unitRepository;
    private RecipeIngredientRepository recipeIngredientRepository;
    private CategoryRepository categoryRepository;
    private RecipeService recipeService;

    @Autowired
    public RecipeController(RecipeRepository recipeRepository, UnitRepository unitRepository, RecipeIngredientRepository recipeIngredientRepository, CategoryRepository categoryRepository, RecipeService recipeService) {
        this.recipeRepository = recipeRepository;
        this.unitRepository = unitRepository;
        this.recipeIngredientRepository = recipeIngredientRepository;
        this.categoryRepository = categoryRepository;
        this.recipeService = recipeService;
    }

    @GetMapping("/recipe")
    public String recipeForm(Model model){
        model.addAttribute("recipe", new Recipe());
        model.addAttribute("ingredient", new RecipeIngredient());
        model.addAttribute("units", unitRepository.findAll());
        model.addAttribute("categories", categoryRepository.findAll());
        model.addAttribute("mode", "add");
        return "recipeForm";
    }

    @PostMapping("/recipe")
    public String add(Recipe recipe, RecipeIngredient recipeIngredient){
        //recipeIngredientRepository.save(recipeIngredient);
        recipeRepository.save(recipe);

        return "redirect:/";
    }

    @GetMapping("/recipes")
    public String recipesList(Model model, @RequestParam (required = false) String sort, RecipeFilters recipeFilters){
        List<Recipe> recipes;
        if(sort != null){
            recipes = recipeService.findAllSorted(sort);
        }
        else if(recipeFilters.getTitle() != null){
            recipes = recipeService.findAllForFilters(recipeFilters);
        }
        else {
            recipes = recipeService.findAllRecipes();
        }
        model.addAttribute("recipes", recipes);
        model.addAttribute("filters", recipeFilters);
        /*
        Page<Recipe> page = recipeService.findAllForFiltersAndSort(recipeFilters, pageable);

        model.addAttribute("recipePage", page);
        model.addAttribute("filters", recipeFilters);
        model.addAttribute("pageable", pageable);
         */
        //Sort sortBy = Sort.by(sort);
        //model.addAttribute("recipes", recipeRepository.findByCategoriesContainsAndTitleContains(recipeFilters.getCategories().get(0).toString(), recipeFilters.getTitle(), sortBy));
        return "recipes";
    }
}
