package pro.sky.cookingappnew.services;

import java.io.File;

public interface FileService {

    boolean saveToFile(String json) ;

    String readFromFile();

    boolean cleanDataFile();

    boolean saveToFile1(String json);

    String readFromFile1();

    boolean cleanDataFile1();

    File getDataFile();

    File getDataFile1();

}
