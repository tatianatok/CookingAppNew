package pro.sky.cookingappnew.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FirstController {

    @GetMapping
    public String appStart () {//Первый должен обрабатывать запросы, и странице должен отображаться текст «Приложение запущено»
        return "Приложение запущено";
    }

    @GetMapping("/info")
    public String info () {
        return "Татьяна Токманцева, CookingApp, 05.06.2023, Любимые рецепты на каждый день";
    }
}