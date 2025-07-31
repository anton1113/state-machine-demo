package com.arash.edu.statemachinedemo.domain;

import lombok.Data;

import java.util.List;

@Data
public class Filter {

    private String name;
    private List<String> values;
}
