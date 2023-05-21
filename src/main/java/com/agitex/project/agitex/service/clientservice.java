package com.agitex.project.agitex.service;

import com.agitex.project.agitex.DTO.clientDTO;
import com.agitex.project.agitex.model.client;
import com.agitex.project.agitex.model.listclient;
import com.agitex.project.agitex.repository.clientrepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.dozermapper.core.Mapper;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

@Service
@Data
public class clientservice {
    public Integer total;
    @Autowired private final clientrepository repository;
    private final Mapper mapper;
    private final ObjectMapper objectMapper;

    public ResponseEntity<List<clientDTO>> excelimporter(InputStream inputstream) {
        try (Workbook workbook = new XSSFWorkbook(inputstream)) {
            Sheet sheet = workbook.getSheetAt(0);
            List<client> listofclients = new ArrayList<>();

            for (int rowIndex = 0; rowIndex <= sheet.getLastRowNum();) {
                Row row = sheet.getRow(rowIndex);
                clientDTO clientdto = new clientDTO();
                clientdto.setNom(row.getCell(0).getStringCellValue());
                clientdto.setPrenom(row.getCell(1).getStringCellValue());
                clientdto.setAge((int) row.getCell(2).getNumericCellValue());
                clientdto.setProfession(row.getCell(3).getStringCellValue());
                clientdto.setRevenu(row.getCell(4).getNumericCellValue());

                client client_mapped = mapper.map(clientdto, client.class);
                listofclients.add(client_mapped);
                rowIndex = rowIndex + 1;
            }
            repository.saveAll(listofclients);
            return new ResponseEntity<>(repository.saveAll(listofclients).stream().map(m->mapper.map(m, clientDTO.class)).collect(Collectors.toList()),HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<List<clientDTO>> csvimporter(MultipartFile file) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withHeader());
            List<client> listofclients = new ArrayList<>();

            for (CSVRecord record : csvParser) {
                client c = new client();
                c.setNom(record.get("nom"));
                c.setPrenom(record.get("prenom"));
                c.setAge(Integer.parseInt(record.get("age")));
                c.setProfession(record.get("profession"));
                c.setRevenu(Double.parseDouble(record.get("revenu")));
                listofclients.add(c);
            }
            repository.saveAll(listofclients);
            return new ResponseEntity<>(repository.saveAll(listofclients).stream().map(m->mapper.map(m, clientDTO.class)).collect(Collectors.toList()),HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @Transactional
    public ResponseEntity<List<clientDTO>> jsonimporter(String filePath) {
        try {
            File file = new File(filePath);
            client[] clients = objectMapper.readValue(file, client[].class);

            for (client c : clients) {
                repository.save(c);
            }
            return new ResponseEntity<>(Arrays.stream(clients).map(m->mapper.map(m, clientDTO.class)).collect(Collectors.toList()),HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    public ResponseEntity<List<clientDTO>> xmlimporter (String filePath) {
        try {
            File file = new File(filePath);
            JAXBContext jaxbContext = JAXBContext.newInstance(listclient.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            listclient clients = (listclient) unmarshaller.unmarshal(file);
            repository.saveAll(clients.getClients());
            return new ResponseEntity<>(repository.saveAll(clients.getClients()).stream().map(m->mapper.map(m, clientDTO.class)).collect(Collectors.toList()),HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    public ResponseEntity<List<clientDTO>> textimporter(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(ResourceUtils.getFile(filePath)))) {
            String line;
            List<client> clients = new ArrayList<>();

            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                client c = new client();
                c.setNom(data[0]);
                c.setPrenom(data[1]);
                c.setAge(Integer.parseInt(data[2]));
                c.setProfession(data[3]);
                c.setRevenu(Double.parseDouble(data[4]));

                clients.add(c);
            }
            repository.saveAll(clients);
            return new ResponseEntity<>(repository.saveAll(clients).stream().map(m->mapper.map(m, clientDTO.class)).collect(Collectors.toList()),HttpStatus.CREATED);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    public ResponseEntity<List<clientDTO>> getclients(){
        try {
            List<clientDTO> allclients = repository.findAll().stream()
                    .map(m->mapper.map(m, clientDTO.class)).collect(Collectors.toList());
            return new ResponseEntity<>(allclients,HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    public ResponseEntity<Integer> getclientsbyprofession(String profession){
        try {
            List<clientDTO> allclients = repository.getclientbyprofession(profession).stream()
                    .map(m->mapper.map(m, clientDTO.class)).collect(Collectors.toList());
            //System.out.println(allclients.size());
            total = 0;
            int moyenne = 0;
            allclients.forEach(client->{
                total = (int) (total + client.getRevenu());
            });
            if(total == 0){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            else {
                moyenne = moyenne + total/allclients.size();
                return new ResponseEntity<>(moyenne,HttpStatus.OK);
            }
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }
}
