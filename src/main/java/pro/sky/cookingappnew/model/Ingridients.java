package pro.sky.cookingappnew.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Ingridients {

    private String titleIngridient;
    private int quantity;
    private String measurement;

}