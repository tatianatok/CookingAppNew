package pro.sky.cookingappnew.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Информация о проекте рецептов", description = "содержит информацию об авторе проекта, описании проекта и даты создания проекта")
public class FirstController {

    @GetMapping
    @Operation(summary = "Запуск приложения", description = "информация о том, что приложение успешно запущено")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "приложение успешно запущено")})
    public String appStart () {
        return "Приложение запущено";
    }

    @GetMapping("/info")
    @Operation(summary = "Информация о проекте", description = "содержит автора проекта, название проекта и дату создания")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "информация успешно найдена")})
    public String info () {
        return "Татьяна Токманцева, CookingApp, 05.06.2023, Любимые рецепты на каждый день";
    }
}
