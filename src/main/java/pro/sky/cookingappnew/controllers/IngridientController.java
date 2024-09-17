package pro.sky.cookingappnew.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import pro.sky.cookingappnew.model.Ingridients;
import pro.sky.cookingappnew.services.IngridientsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@Tag(name = "Ингредиенты", description = "CRUD операции и другие эндпоинты для работы с ингредиентами")
@RequestMapping("/ingredient")
public class IngridientController {

    private final IngridientsService ingridientsService;

    public IngridientController(IngridientsService ingridientsService) {
        this.ingridientsService = ingridientsService;
    }

    @PostMapping
    @Operation(summary = "Добавление ингредиента",
            description = "для добавления ингредиента требуется названиие, количество и единица измерения")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "ингредиент был успешно добавлен",
            content = {@Content(mediaType = "application/json")})})
        @Operation (summary = "Добавить новый ингредиент", description = "Добавить новый ингредиент")
    public Ingridients addNewIngridient(@RequestBody Ingridients ingridient) {
        ingridientsService.addNewIngridient(ingridient);
        return ingridient;
    }

    @GetMapping("{id}")
    @Operation(summary = "Поиск ингредиента", description = "нужно искать ингредиент по id")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "ингредиент был найден"),
            @ApiResponse(responseCode = "400", description = "плохой запрос, отправлен некорректный запрос серверу"),
            @ApiResponse(responseCode = "500", description = "сервер столкнулся с неожиданной ошибкой, которая помешала ему выполнить запрос")})
    public Ingridients getIngridient(@RequestParam Long idIngridient) {
        return ingridientsService.getIngridient(idIngridient);
    }

    @GetMapping
    @Operation(summary = "Получение всех ингредиентов",
            description = "получение списка List всех ингредиентов")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "ингредиенты были успешно получены",
            content = {@Content(mediaType = "формат List")})})
    @Operation (summary = "Получить все ингредиенты", description = "Получить все ингредиенты")
    public ResponseEntity<Map<Long, Ingridients>> getAllIngridient() {
        Map<Long, Ingridients> ingridients = ingridientsService.getAllIngridient();
        if (ingridients == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(ingridients);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Редактирование ингредиента",
            description = "можно редактировать по id как один параметр, так и несколько в том числе название, количество, единицу измерения")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "ингредиент был успешно отредактирован"),
            @ApiResponse(responseCode = "400", description = "плохой запрос, отправлен некорректный запрос серверу"),
            @ApiResponse(responseCode = "500", description = "сервер столкнулся с неожиданной ошибкой, которая помешала ему выполнить запрос")})
    @Operation (summary = "Изменить ингредиент", description = "Изменить ингредиент")
    public ResponseEntity<Ingridients> putIngridient(@PathVariable Long idIngridient, @RequestBody Ingridients ingridient) {
        Ingridients ingridient1 = ingridientsService.putIngridient(idIngridient, ingridient);
        if (ingridient1 == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(ingridient);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удаление ингредиента",
            description = "можно удалить ингредиент только по id")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "ингредиент успешно удален"),
            @ApiResponse(responseCode = "400", description = "плохой запрос, отправлен некорректный запрос серверу"),
    @ApiResponse(responseCode = "500", description = "сервер столкнулся с неожиданной ошибкой, которая помешала ему выполнить запрос")})
    public ResponseEntity<Void> deleteIngridient(@PathVariable Long idIngridient) {
        if (ingridientsService.deleteIngridient(idIngridient)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
