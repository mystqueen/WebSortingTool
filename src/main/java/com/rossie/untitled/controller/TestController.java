package com.rossie.untitled.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class TestController {

    @ResponseBody
    @GetMapping("/try")
    public ResponseEntity<String> response(){
        return ResponseEntity.ok("Doneeee");
    }
}
