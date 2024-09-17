package pro.sky.cookingappnew.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import pro.sky.cookingappnew.model.Ingridients;
import pro.sky.cookingappnew.services.FileService;
import pro.sky.cookingappnew.services.IngridientsService;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.TreeMap;

@Service
public class EngridientsServiceImpl implements IngridientsService {

    private final FileService filesService;

    private Map<Long, Ingridients> ingridient = new TreeMap<>();
    private Long idIngridient = 1L;

    public EngridientsServiceImpl(FileService filesService) {
        this.filesService = filesService;
    }

    @Override
    public Ingridients addNewIngridient(Ingridients ingridients) {
       ingridient.putIfAbsent(idIngridient, ingridients);
       idIngridient++;
       saveToFile1();
       return ingridients;
    }

    @Override
    public Ingridients getIngridient(Long idIngridient) {
        saveToFile1();
        return ingridient.get(idIngridient);
    }

    @Override
    public Map<Long, Ingridients> getAllIngridient() {
        return ingridient;
    }

    @Override
    public Ingridients putIngridient(Long idIngridient, Ingridients ingridient) {
        if (this.ingridient.containsKey(idIngridient)) {
            this.ingridient.put(idIngridient, ingridient);
            saveToFile1();
            return ingridient;
        }
        return null;
    }

    @Override
    public boolean deleteIngridient(Long idIngridient) {
        saveToFile1();
        return ingridient.remove(idIngridient) != null;
    }


    private void saveToFile1(){
        try {
            String json = new ObjectMapper().writeValueAsString(ingridient);
            filesService.saveToFile1(json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }


    private void readFromFile1(){
        String json = filesService.readFromFile1();
        try {
            ingridient = new ObjectMapper().readValue(json, new TypeReference<TreeMap<Long, Ingridients>>(){
            });
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @PostConstruct
    private void init () {
        readFromFile1();
    }
}