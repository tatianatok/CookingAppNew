package pro.sky.cookingappnew.controllers;

import io.swagger.v3.oas.annotations.Operation;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pro.sky.cookingappnew.model.Ingridients;
import pro.sky.cookingappnew.model.Recipe;
import pro.sky.cookingappnew.services.FileService;
import pro.sky.cookingappnew.services.RecipeService;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.charset.StandardCharsets;

@RestController
@Tag(name = "Файлы", description = "Операции по работе с файлами рецептов и ингредиентов")
@RequestMapping("/files")
public class FileController {

    private FileService fileService;
    private RecipeService recipeService;

    public FileController(FileService fileService, RecipeService recipeService) {
        this.fileService = fileService;
        this.recipeService = recipeService;
    }

   @GetMapping("download/recipes")
    @Operation(summary = "Скачать рецепты в виде json-файла")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "файл успешно скачался"),
            @ApiResponse(responseCode = "400", description = "плохой запрос, отправлен некорректный запрос серверу"),
            @ApiResponse(responseCode = "500", description = "сервер столкнулся с неожиданной ошибкой, которая помешала ему выполнить запрос")})
    public ResponseEntity<InputStreamResource> downloadDataFile() throws FileNotFoundException {
        File file = fileService.getDataFile();
        if (file.exists()) {
            InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .contentLength(file.length())
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"recipeLog.json\"")
                    .body(resource);

        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @GetMapping("upload/recipes")
    @Operation(summary = "Загрузить файл с рецептами", description = "принимает json-файл с рецептами и заменяет сохраненный на жестком (локальном) диске файл на новый")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "файл успешно загружен"),
            @ApiResponse(responseCode = "400", description = "плохой запрос, отправлен некорректный запрос серверу"),
            @ApiResponse(responseCode = "500", description = "сервер столкнулся с неожиданной ошибкой, которая помешала ему выполнить запрос")})
    @PostMapping(value = "/importRec", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> uploadDataFile(@RequestParam MultipartFile file) {
        fileService.cleanDataFile();
        File dataFile = fileService.getDataFile();

        try (FileOutputStream fos = new FileOutputStream(dataFile)) {
            IOUtils.copy(file.getInputStream(), fos);
            return ResponseEntity.ok().build();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @GetMapping("upload/ingredients")
    @Operation(summary = "Загрузить файл с ингредиентами", description = "принимает json-файл с ингредиентами и заменяет сохраненный на жестком (локальном) диске файл на новый")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "файл успешно загружен"),
            @ApiResponse(responseCode = "400", description = "плохой запрос, отправлен некорректный запрос серверу"),
            @ApiResponse(responseCode = "500", description = "сервер столкнулся с неожиданной ошибкой, которая помешала ему выполнить запрос")})
    @PostMapping(value = "/importIng", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> uploadDataFile1(@RequestParam MultipartFile file) {
        fileService.cleanDataFile1();
        File dataFile = fileService.getDataFile1();

        try (FileOutputStream fos1 = new FileOutputStream(dataFile)) {
            IOUtils.copy(file.getInputStream(), fos1);
            return ResponseEntity.ok().build();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @Operation(
            summary = "Загружаем список рецептов в формате txt"
    )
    @GetMapping("/exportAllRecipes")
    public void exportAllRecipes(HttpServletResponse response) throws IOException {
        response.setContentType("text/plain");
        response.setHeader("Content-Disposition", "attachment; filename=sample.txt");
        PrintWriter writer = new PrintWriter(new OutputStreamWriter(response.getOutputStream(), StandardCharsets.UTF_8));
        for (Recipe recipe : recipeService.getAllRecipe().values()) {
            writer.println(recipe.getRecipeTitle());
            writer.println(String.format("Время приготовления: %d минут", recipe.getTime()));
            writer.println("Ингредиенты:");
            for (Ingridients ingredients : recipe.getIngridients()) {
                writer.println(String.format("\t%s - %d %s", ingredients.getTitleIngridient(), ingredients.getQuantity(), ingredients.getMeasurement()));
            }
            writer.println("Инструкция приготовления");
            for (int i = 0; i < recipe.getPreparation().size(); i++) {
                writer.println(String.format("%d. %s", i + 1, recipe.getPreparation().get(i)));
            }
            writer.println(" ");
        }
        writer.flush();
    }

}
