package com.demo.restservice.models;

import lombok.Data;

@Data
public class SquareRes {
    private Long number;
    private Long square;

    public SquareRes(Long number, Long square) {
        this.number = number;
        this.square = square;
    }
}
