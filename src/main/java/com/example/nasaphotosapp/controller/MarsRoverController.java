package com.example.nasaphotosapp.controller;

import com.example.nasaphotosapp.service.MarsRoverService;
import lombok.extern.slf4j.Slf4j;
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
    public ResponseEntity<List<String>> processDatesFile() throws IOException
    {
        List<String> downloadedImages = marsRoverService.processImagesFromDateFile("dates.txt");
        return ResponseEntity.ok(downloadedImages);
    }

    @GetMapping("/image/{date}")
    public ResponseEntity<List<String>> downloadImageForDate(@PathVariable String date)
    {
        List<String> downloadedImages = marsRoverService.downloadImagesForDate(date);
        if (downloadedImages != null && !downloadedImages.isEmpty())
        {
            return ResponseEntity.ok(downloadedImages);
        } else
        {
            return ResponseEntity.notFound().build();
        }
    }
}
