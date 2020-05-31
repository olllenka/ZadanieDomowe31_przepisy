package com.example.cookbook.recipe;

import com.example.cookbook.dictionary.category.CategoryRepository;
import com.example.cookbook.dictionary.diet.DietRepository;
import com.example.cookbook.dictionary.difficulty.DifficultyRepository;
import com.example.cookbook.dictionary.unit.UnitRepository;
import com.example.cookbook.recipeIngredient.RecipeIngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class RecipeController {

    private RecipeService recipeService;
    private RecipeIngredientService recipeIngredientService;
    private UnitRepository unitRepository;
    private CategoryRepository categoryRepository;
    private DietRepository dietRepository;
    private DifficultyRepository difficultyRepository;

    @Autowired
    public RecipeController(UnitRepository unitRepository, CategoryRepository categoryRepository, RecipeService recipeService, DietRepository dietRepository, DifficultyRepository difficultyRepository, RecipeIngredientService recipeIngredientService) {
        this.recipeService = recipeService;
        this.recipeIngredientService = recipeIngredientService;
        this.unitRepository = unitRepository;
        this.categoryRepository = categoryRepository;
        this.dietRepository = dietRepository;
        this.difficultyRepository = difficultyRepository;
    }

    @GetMapping("/recipeForm")
    public String recipeForm(Model model) {
        Recipe recipe = recipeService.prepareRecipeWithTenIngredients();

        model.addAttribute("recipe", recipe);
        model.addAttribute("categories", categoryRepository.findAll());
        model.addAttribute("diets", dietRepository.findAll());
        model.addAttribute("difficulties", difficultyRepository.findAll());
        model.addAttribute("units", unitRepository.findAll());

        model.addAttribute("mode", "add");
        return "recipeForm";
    }

    @PostMapping("/recipeForm")
    public String add(@ModelAttribute("recipe") Recipe recipe) {
        recipeService.saveRecipe(recipe);
        recipeIngredientService.updateRecipeIngredientsAfterRecipeSave(recipe);
        return "redirect:/recipes";
    }

    @GetMapping("/edit/{id}")
    public String editForm(Model model, @PathVariable Long id){
        Optional<Recipe> recipeOptional = recipeService.findById(id);

        if (recipeOptional.isPresent()) {
            Recipe recipe = recipeOptional.get();
            recipeService.addFiveEmptyRecipeIngredients(recipe);
            model.addAttribute("recipe", recipe);
            model.addAttribute("categories", categoryRepository.findAll());
            model.addAttribute("diets", dietRepository.findAll());
            model.addAttribute("difficulties", difficultyRepository.findAll());
            model.addAttribute("units", unitRepository.findAll());
            model.addAttribute("mode", "edit");
            return "recipeForm";
        } else {
            return "redirect:/";
        }
    }

    @PostMapping("/edit")
    public String edit(@ModelAttribute("recipe") Recipe recipe){
        recipeService.updateRecipe(recipe);
        recipeIngredientService.updateRecipeIngredientsAfterRecipeEdition(recipe);
        return "redirect:recipes";
    }

    @GetMapping("/remove/{id}")
    public String remove(@PathVariable Long id){
        recipeService.removeRecipe(id);
        return "redirect:/recipes";
    }

    @GetMapping("/recipes")
    public String recipesList(Model model, @RequestParam(required = false) String sort, RecipeFilters recipeFilters) {
        List<Recipe> recipes;
        if (sort != null) {
            recipes = recipeService.findAllSorted(sort);
        } else if (recipeFilters.getTitle() != null) {
            recipes = recipeService.findAllFiltered(recipeFilters);
        } else {
            recipes = recipeService.findAllRecipes();
        }
        model.addAttribute("recipes", recipes);
        model.addAttribute("filters", recipeFilters);
        return "recipes";
    }

    @GetMapping("/recipe/{id}")
    public String showRecipe(Model model, @PathVariable Long id, @RequestParam(required = false) String likeIt) {
        Optional<Recipe> recipeOptional = recipeService.findById(id);

        if (recipeOptional.isPresent()) {
            Recipe recipe = recipeOptional.get();
            model.addAttribute("recipe", recipe);

            if("Lubie".equals(likeIt)){
                int newLikeCounter = recipe.getLikeCounter() +1;
                recipeService.updateNumberOfLikes(recipe.getId(), newLikeCounter);
            }
            return "recipe";
        } else {
            return "redirect:/";
        }
    }
}
