package pro.sky.cookingappnew.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Recipe {

    private String recipeTitle;
    private int time;
    private List<Ingridients> ingridients;
    private List<String> preparation;

}

