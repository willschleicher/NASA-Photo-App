package com.example.nasaphotosapp.model;

import lombok.Data;

@Data
public class Rover
{
    private Long id;
    private String name;
    private String landing_date;
    private String launch_date;
    private String status;
}
