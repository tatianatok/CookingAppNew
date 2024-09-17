package pro.sky.cookingappnew.services;

import pro.sky.cookingappnew.model.Ingridients;
import java.util.Map;

public interface IngridientsService {
    Ingridients addNewIngridient(Ingridients ingridients);

    Ingridients getIngridient(Long idIngridient);

    Map<Long, Ingridients> getAllIngridient();

    Ingridients putIngridient(Long idIngridient, Ingridients ingridient);

    boolean deleteIngridient(Long idIngridient);
}