package com.example.nasaphotosapp.model;

import lombok.Data;

import java.util.List;

@Data
public class MarsRoverResponse
{
    private List<MarsPhoto> photos;
}
