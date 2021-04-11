package com.demo.restservice.controllers;

import com.demo.restservice.models.SquareRes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/square/")
public class NumberController {
    @GetMapping("{number}")
    public ResponseEntity<Object> getSquare(@PathVariable Long number){
        var res = new SquareRes(number,number*number);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
}
