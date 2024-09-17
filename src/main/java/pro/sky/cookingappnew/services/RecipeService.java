package pro.sky.cookingappnew.services;

import pro.sky.cookingappnew.model.Recipe;

import java.util.Map;


public interface RecipeService {

    Recipe addNewRecipe(Recipe recipe);

    Recipe getRecipe(Long recipeId);

    Map<Long, Recipe> getAllRecipe();

    Recipe putRecipe(Long idRec, Recipe recipe);

    boolean deleteRecipe(Long recipeId);

}