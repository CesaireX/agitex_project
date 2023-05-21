package com.agitex.project.agitex.controller;

import com.agitex.project.agitex.DTO.clientDTO;
import com.agitex.project.agitex.service.clientservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
@RestController
public class clientcontroller {
    @Autowired
    private clientservice service;

    public clientcontroller(clientservice service) {
        this.service = service;
    }

    @PostMapping("/excel")
    public ResponseEntity<List<clientDTO>> importExcelData(@RequestParam("file") MultipartFile file) throws IOException {
        if (!file.isEmpty()) {
            InputStream inputStream = file.getInputStream();
                return service.excelimporter(inputStream);
            }
        else{
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/csv")
    public ResponseEntity<List<clientDTO>> importcsvData(@RequestParam("file") MultipartFile file) {
        if (!file.isEmpty()) {
               return service.csvimporter(file);
        }
        else{
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping("/xml")
    public ResponseEntity<List<clientDTO>> importxmlData(@RequestParam("file") String filepath) {
        if (!filepath.isEmpty()) {
            return service.xmlimporter(filepath);
        }
        else{
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/text")
    public ResponseEntity<List<clientDTO>> importtextData(@RequestParam("file") String filepath) {
        if (!filepath.isEmpty()) {
            return service.textimporter(filepath);
        }
        else{
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/json")
    public ResponseEntity<List<clientDTO>> importJsonData(@RequestParam("file") String file) {
       return service.jsonimporter(file);
    }

    @PostMapping("/moyennebyprofession/{profession}")
    public ResponseEntity<Integer> moyennebyprofession (@PathVariable String profession) {
        return service.getclientsbyprofession(profession);
    }

    @GetMapping("/client")
    public ResponseEntity<List<clientDTO>> getlistofclients() {
        return service.getclients();
    }
}
