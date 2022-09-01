package com.example.search.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RestController
public class SearchController {

    private final ExecutorService pool = Executors.newCachedThreadPool();

//    @GetMapping("/employees/{id}")
//    public ResponseEntity<EmployeeResponseDTO> getEmpById(@PathVariable String id) {
//        return new ResponseEntity<>(employeeService.getById(id), HttpStatus.OK);
//    }

    @GetMapping("/search/{id}")
    @ResponseBody
    public ResponseEntity<?> getDetails(@PathVariable String id, HttpServletRequest request) {

        List<String> elephantList = Arrays.asList(id.split(","));
        final List<String> final_result = new ArrayList<>();
        int i;

        if (elephantList.size() > 5) {
            return new ResponseEntity<>("Too many input", HttpStatus.OK);
        }
        else {

//        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> {
//            String uri = "http://localhost:8000/employees/" + id;
//
//            RestTemplate restTemplate = new RestTemplate();
//            String result = restTemplate.getForObject(uri, String.class);
//
//            final_result.add(result);
//        };
//        final CompletableFuture[] completableFutures = {cf1, cf2};
//        CompletableFuture<String> cf1 = CompletableFuture.supplyAsync(() -> {
//            String uri = "http://localhost:8000/employees/" + 1;
//            RestTemplate restTemplate = new RestTemplate();
//            String result = restTemplate.getForObject(uri, String.class);
//            final_result.add(result);
//            return result;
//        }, pool);
//
//        CompletableFuture<String> cf2 = CompletableFuture.supplyAsync(() -> {
//            String uri = "http://localhost:8000/employees/" + 2;
//            RestTemplate restTemplate = new RestTemplate();
//            String result = restTemplate.getForObject(uri, String.class);
//            final_result.add(result);
//            return result;
//        }, pool);
//
//        final CompletableFuture[] completableFutures = {cf1, cf2};
//        final CompletableFuture[] completableFutures;
//        for (i = 0; i < elephantList.size(); i++) {
//            CompletableFuture<String> temp = CompletableFuture.supplyAsync(() -> {
//            String uri = "http://localhost:8000/employees/" + 2;
//            RestTemplate restTemplate = new RestTemplate();
//            String result = restTemplate.getForObject(uri, String.class);
//            final_result.add(result);
//            return result;
//        }, pool

            CompletableFuture<String> cf1 = new CompletableFuture<>();
            CompletableFuture<String> cf2 = new CompletableFuture<>();
            CompletableFuture<String> cf3 = new CompletableFuture<>();
            CompletableFuture<String> cf4 = new CompletableFuture<>();
            CompletableFuture<String> cf5 = new CompletableFuture<>();

            CompletableFuture[] completableFutures = {cf1, cf2, cf3, cf4, cf5};

            for (i = 0; i < elephantList.size(); i++) {
                final String temp = String.valueOf(elephantList.get(i));
                completableFutures[i] = CompletableFuture.supplyAsync(() -> {
                    String uri = "http://localhost:8000/employees/" + temp;
                    RestTemplate restTemplate = new RestTemplate();
                    String result = restTemplate.getForObject(uri, String.class);
                    final_result.add(result);
                    return result;
                }, pool);
            }

            for (i = elephantList.size(); i < 5; i++) {
                completableFutures[i] = CompletableFuture.supplyAsync(() -> 0);
            }

            CompletableFuture.allOf(completableFutures)
                    .thenApply(xx -> {
                        List<String> res = new ArrayList<>();
                        for (CompletableFuture<String> future : completableFutures) {
                            res.add(future.join());
                        }
                        return res;
                    }).thenApply(arrList -> arrList.toString())
//                .thenAccept(str -> final_result.add(str))
                    .join();


            return new ResponseEntity<>(final_result.toString(), HttpStatus.OK);
        }
    }



    @GetMapping("/search")
    public ResponseEntity<?> getDetails() {
        return new ResponseEntity<>("this is search service", HttpStatus.OK);
    }
}
