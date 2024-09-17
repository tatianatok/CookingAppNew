package pro.sky.cookingappnew.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pro.sky.cookingappnew.services.FileService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class FileServiceImpl implements FileService {

    @Value("${path.to.data.file}")
    private String dataFilePath;

    @Value("${name.to.data.file}")
    private String dataFileNameRecipe;

    @Value("${name.to.data.file1}")
    private String dataFileNameIngridient;


    @Override
    public boolean saveToFile(String json) {
        try {
            cleanDataFile();
            Files.writeString(Path.of(dataFilePath, dataFileNameRecipe), json);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    @Override
    public String readFromFile() {
        try {
            return Files.readString(Path.of(dataFilePath, dataFileNameRecipe));
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean cleanDataFile() {
        try {
            final Path path = Path.of(dataFilePath, dataFileNameRecipe);
            Files.deleteIfExists(path);
            Files.createFile(path);
        } catch (IOException e) {
            e.printStackTrace();

        }
        return false;
    }

    @Override
    public boolean saveToFile1(String json) {
        try {
            cleanDataFile1();
            Files.writeString(Path.of(dataFilePath, dataFileNameIngridient), json);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public String readFromFile1() {
        try {
            return Files.readString(Path.of(dataFilePath, dataFileNameIngridient));
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean cleanDataFile1() {
        try {
            final Path path = Path.of(dataFilePath, dataFileNameIngridient);
            Files.deleteIfExists(path);
            Files.createFile(path);
        } catch (IOException e) {
            e.printStackTrace();

        }
        return false;
    }

    @Override
    public File getDataFile (){
        return new File(dataFilePath + "/" + dataFileNameRecipe);
    }

    @Override
    public File getDataFile1 (){
        return new File(dataFilePath + "/" + dataFileNameIngridient);
    }

}




