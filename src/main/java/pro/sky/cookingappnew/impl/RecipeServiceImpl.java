package pro.sky.cookingappnew.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import pro.sky.cookingappnew.model.Recipe;
import pro.sky.cookingappnew.services.FileService;
import pro.sky.cookingappnew.services.RecipeService;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.PrintWriter;
import java.util.Map;
import java.util.TreeMap;

@Service
public class RecipeServiceImpl implements RecipeService {

    private final FileService filesService;

    private Map<Long, Recipe> recipes = new TreeMap<>();
    private Long recipeId = 1L;

    public RecipeServiceImpl(FileService filesService) {
        this.filesService = filesService;
    }

    @Override
    public Recipe addNewRecipe(Recipe recipe) {
        recipes.putIfAbsent(recipeId, recipe);
        recipeId++;
        saveToFile();
        return recipe;
    }

    @Override
    public Recipe getRecipe(Long recipeId) {
        saveToFile();
        return recipes.get(recipeId);
    }

    @Override
    public Map<Long, Recipe> getAllRecipe() {
        return recipes;
    }

    @Override
    public Recipe putRecipe(Long recipeId, Recipe recipe) {
        if (this.recipes.containsKey(recipeId)) {
            this.recipes.put(recipeId, recipe);
            saveToFile();
            return recipe;
        }
        return null;
    }

    @Override
    public boolean deleteRecipe(Long recipeId) {
        saveToFile();
        return recipes.remove(recipeId) != null;
    }

    private void saveToFile() {
        try {
            String json = new ObjectMapper().writeValueAsString(recipes);
            filesService.saveToFile(json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private void readFromFile() {
        String json = filesService.readFromFile();
        try {
            recipes = new ObjectMapper().readValue(json, new TypeReference<TreeMap<Long, Recipe>>() {
            });
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @PostConstruct
    private void init() {
        readFromFile();
    }

}