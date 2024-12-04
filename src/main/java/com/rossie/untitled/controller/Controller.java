package com.rossie.untitled.controller;

import com.rossie.untitled.HateoasGenerator;
import com.rossie.untitled.InvalidInputException;
import com.rossie.untitled.model.DataModel;
import com.rossie.untitled.model.SortRequest;
import com.rossie.untitled.service.DataService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@Validated
public class Controller {
    private final DataService dataService;
    private final HateoasGenerator hateoasGenerator;

    @GetMapping("/data")
    @ResponseBody
    public List<DataModel> getAllData(){
        return dataService.getAllData();
    }

    @GetMapping("/data/{id}")
    @ResponseBody
    public ResponseEntity<?> getDataById(@PathVariable("id") int id){
        try {
            if (id <= 0) {
                throw new InvalidInputException("Invalid ID: ID must be a positive integer");
            }
            DataModel data = dataService.getDataById(id);
            hateoasGenerator.addHateoasLinks(data);
            return ResponseEntity.ok(data);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @ResponseBody
    @GetMapping("algorithm/{algorithm}")
    public ResponseEntity<?> getDataByAlgorithm(@PathVariable("algorithm") String algorithm){
        try {
            if (algorithm == null || algorithm.trim().isEmpty()) {
                throw new InvalidInputException("Algorithm name cannot be empty");
            }
            List<DataModel> data = dataService.getDataByAlgorithm(algorithm);
            return ResponseEntity.ok(data);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @ResponseBody
    @PostMapping("/data/add")
    public ResponseEntity<?> createData(@RequestBody List<Integer> list) {
        try {
            if (list == null || list.isEmpty() || list.stream().anyMatch(Objects::isNull)) {
                throw new InvalidInputException("Input list cannot be null, empty, or contain null values");
            }

            DataModel dataModel = dataService.createData(list);
            hateoasGenerator.addHateoasLinks(dataModel);
            return ResponseEntity.ok(dataModel);
        } catch (InvalidInputException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }
    }

    @PostMapping("/sort")
    @ResponseBody
    public ResponseEntity<?> sortData(@RequestBody SortRequest request) {
        try {
            // Validate sort request
            if (request == null) {
                throw new InvalidInputException("Sort request cannot be null");
            }
            if (request.getId() <= 0) {
                throw new InvalidInputException("Invalid ID: ID must be a positive integer");
            }
            if (request.getAlgorithm() == null || request.getAlgorithm().trim().isEmpty()) {
                throw new InvalidInputException("Algorithm name cannot be empty");
            }

            DataModel sortedData = dataService.sort(request);
            hateoasGenerator.addHateoasLinks(sortedData);
            return ResponseEntity.ok(sortedData);
        } catch (InvalidInputException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }
    }

    @ResponseBody
    @DeleteMapping("data/{id}/delete")
    public ResponseEntity<String> deleteData(@PathVariable("id") int id){
        try {
            if (id <= 0) {
                throw new InvalidInputException("Invalid ID: ID must be a positive integer");
            }
            dataService.deleteData(id);
            return ResponseEntity.ok("Data Deleted");
        } catch (InvalidInputException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }
    }
}
