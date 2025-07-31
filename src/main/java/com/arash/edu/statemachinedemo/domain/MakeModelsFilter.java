package com.arash.edu.statemachinedemo.domain;

import lombok.Data;

import java.util.List;

@Data
public class MakeModelsFilter {

    private String make;
    private List<String> models;
}
