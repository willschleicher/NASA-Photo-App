package com.example.nasaphotosapp.service;

import com.example.nasaphotosapp.client.NasaApiClient;
import com.example.nasaphotosapp.model.MarsPhoto;
import com.example.nasaphotosapp.model.MarsRoverResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
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
            String image = downloadImageForDate(date);
            if (image != null)
            {
                downloadedImages.add(image);
            }
        }

        return downloadedImages;
    }

    public String downloadImageForDate(String date)
    {
        MarsRoverResponse response = nasaApiClient.getMarsRoverPhotos(date);

        if (response.getPhotos() == null || response.getPhotos().isEmpty())
        {
            return null;
        }

        // For simplicity, just get the first photo
        // TODO: perhaps in front-end app show imageUrls to look nice
        MarsPhoto photo = response.getPhotos().getFirst();
        String imageUrl = photo.getImg_src();

        try
        {
            // Create directory if it doesn't exist
            Path directory = Paths.get(imagesDirectory + "/" + date);
            Files.createDirectories(directory);

            // Extract filename from URL
            String fileName = getFileNameFromUrl(imageUrl);
            Path destinationPath = directory.resolve(fileName);

            // Download the image
            // TODO: fix this method, it's getting a 301 redirect error - > try using rest client or something instead??
            URL url = new URL(imageUrl);
            Files.copy(url.openStream(), destinationPath, StandardCopyOption.REPLACE_EXISTING);

            return destinationPath.toString();
        } catch (IOException e)
        {
            throw new RuntimeException("Failed to download image from: " + imageUrl, e);
        }
    }

    private String getFileNameFromUrl(String url)
    {
        return url.substring(url.lastIndexOf('/') + 1);
    }
}
