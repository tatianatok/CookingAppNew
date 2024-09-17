package pro.sky.cookingappnew.controllers;

import org.apache.commons.io.IOUtils;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pro.sky.cookingappnew.services.FileService;

import java.io.*;

@RestController
@RequestMapping("/files")
public class FileController {

    private FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @GetMapping("/exportRec")
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

    @PostMapping(value = "/importRec", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> uploadDataFile (@RequestParam MultipartFile file){
        fileService.cleanDataFile();
        File dataFile = fileService.getDataFile();

        try (FileOutputStream fos = new FileOutputStream(dataFile)){
            IOUtils.copy(file.getInputStream(),fos);
            return ResponseEntity.ok().build();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @PostMapping(value = "/importIng", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> uploadDataFile1 (@RequestParam MultipartFile file){
        fileService.cleanDataFile1();
        File dataFile = fileService.getDataFile1();

        try (FileOutputStream fos1 = new FileOutputStream(dataFile)){
            IOUtils.copy(file.getInputStream(),fos1);
            return ResponseEntity.ok().build();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}

//Создайте три эндпоинта.
//
//Функционал и требования к ним:
//
// Первый эндпоинт позволяет скачать все рецепты в виде json-файла.
// Второй эндпоинт принимает json-файл с рецептами и заменяет сохраненный на жестком (локальном) диске файл на новый.
// Третий эндпоинт принимает json-файл с ингредиентами и заменяет сохраненный на жестком (локальном) диске файл на новый.
//В результате у вас должно получиться дописанное приложение, в котором реализовано три эндопинта, перечисленные выше.
//
//Критерии оценки:
//Реализован отдельный контроллер, который работает с файлами
//Созданы три метода в контроллере, которые реализуют работу с файлами в формате json
//Обработаны ошибки в дописанном приложении