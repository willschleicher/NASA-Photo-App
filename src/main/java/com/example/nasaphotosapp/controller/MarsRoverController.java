package com.example.nasaphotosapp.controller;

import com.example.nasaphotosapp.service.MarsRoverService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/marsrover")
public class MarsRoverController
{

    private final MarsRoverService marsRoverService;

    public MarsRoverController(MarsRoverService marsRoverService)
    {
        this.marsRoverService = marsRoverService;
    }

    @PostMapping("/process-dates")
    public ResponseEntity<List<String>> processDatesFile()
    {
        try
        {
            List<String> downloadedImages = marsRoverService.processImagesFromDateFile("dates.txt");
            return ResponseEntity.ok(downloadedImages);
        } catch (IOException e)
        {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/image/{date}")
    public ResponseEntity<String> downloadImageForDate(@PathVariable String date)
    {
        String downloadedImage = marsRoverService.downloadImageForDate(date);
        if (downloadedImage != null)
        {
            return ResponseEntity.ok(downloadedImage);
        } else
        {
            return ResponseEntity.notFound().build();
        }
    }
}
