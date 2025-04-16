package com.example.nasaphotosapp.model;

import lombok.Data;

@Data
public class MarsPhoto
{
    private Long id;
    private Integer sol;
    private Camera camera;
    private String img_src;
    private String earth_date;
    private Rover rover;
}
