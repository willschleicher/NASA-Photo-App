package com.example.nasaphotosapp.service;

import com.example.nasaphotosapp.client.NasaApiClient;
import com.example.nasaphotosapp.model.MarsPhoto;
import com.example.nasaphotosapp.model.MarsRoverResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

@Service
public class MarsRoverService
{

    private final NasaApiClient nasaApiClient;
    private final DateParserService dateParserService;

    @Value("${nasa.images.directory}")
    private String imagesDirectory;

    public MarsRoverService(NasaApiClient nasaApiClient, DateParserService dateParserService)
    {
        this.nasaApiClient = nasaApiClient;
        this.dateParserService = dateParserService;
    }

    public List<String> processImagesFromDateFile(String dateFilePath) throws IOException
    {
        List<String> dates = dateParserService.parseDatesFromFile(dateFilePath);
        List<String> downloadedImages = new ArrayList<>();

        for (String date : dates)
        {
            List<String> imagesForDate = downloadImagesForDate(date);
            downloadedImages.addAll(imagesForDate);
        }

        return downloadedImages;
    }

    public List<String> downloadImagesForDate(String date)
    {
        MarsRoverResponse response = nasaApiClient.getMarsRoverPhotos(date);

        if (response.getPhotos() == null || response.getPhotos().isEmpty())
        {
            return List.of();
        }

        List<String> savedPaths = new ArrayList<>();

        try
        {
            Path directory = Paths.get(imagesDirectory, date);
            Files.createDirectories(directory);

            for (MarsPhoto photo : response.getPhotos())
            {
                String imageUrl = photo.getImg_src();

                // Workaround for redirect to HTTPS issue
                imageUrl = imageUrl
                        .replace("http://mars.jpl.nasa.gov", "https://mars.nasa.gov");

                String fileName = getFileNameFromUrl(imageUrl);
                Path destination = directory.resolve(fileName);

                try (InputStream in = new URL(imageUrl).openStream())
                {
                    Files.copy(in, destination, StandardCopyOption.REPLACE_EXISTING);
                    savedPaths.add(destination.toString());
                }
            }

            return savedPaths;
        } catch (IOException e)
        {
            throw new RuntimeException("Failed to download images for date " + date, e);
        }
    }

    private String getFileNameFromUrl(String url)
    {
        return url.substring(url.lastIndexOf('/') + 1);
    }
}
