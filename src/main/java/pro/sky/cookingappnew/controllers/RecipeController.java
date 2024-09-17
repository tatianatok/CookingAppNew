package pro.sky.cookingappnew.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import pro.sky.cookingappnew.model.Recipe;
import pro.sky.cookingappnew.services.RecipeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@Tag(name = "Рецепты", description = "CRUD операции и другие эндпоинты для работы с рецептами")
@RequestMapping("/recipe")
public class RecipeController {
    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @PostMapping
    @Operation(summary = "Добавление рецепта",
            description = "для добавления рецепта требуется названиие, время приготовления, список ингредиентов и шаги приготовления")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "рецепт был успешно добавлен",
            content = {@Content(mediaType = "application/json")})})
    public Recipe addNewRecipe(@RequestBody Recipe recipe) {
        recipeService.addNewRecipe(recipe);
        return recipe;
    }

    @GetMapping("{id}")
    @Operation(summary = "Поиск рецепта", description = "нужно искать рецепт по id")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "рецепт найден")})
    public Recipe getRecipe(@RequestParam Long recipeId) {
        return recipeService.getRecipe(recipeId);
    }

    @GetMapping
    @Operation(summary = "Получение  всех рецептов",
            description = "получение списка List всех рецептов")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "рецепты были успешно получены",
            content = {@Content(mediaType = "формат List")})})
    public ResponseEntity<Map<Long, Recipe>> getAllRecipe()  {
        Map<Long, Recipe> recipe = recipeService.getAllRecipe();
        if (recipe == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(recipe);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Редактирование рецепта",
            description = "можно редактировать по id как один параметр, так и несколько в том числе название, время приготовления, список ингредиентов и шаги приготовления")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "рецепт был успешно отредактирован")})
    public ResponseEntity<Recipe> putRecipe(@PathVariable Long recipeId, @RequestBody Recipe recipe) {
        Recipe recipe1 = recipeService.putRecipe(recipeId, recipe);
        if (recipe1 == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(recipe);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удаление рецепта",
            description = "можно удалить рецепт только по id")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "рецепт успешно удален")}
    public ResponseEntity<Void> deleteRecipe(@PathVariable Long recipeId) {
        if (recipeService.deleteRecipe(recipeId)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
