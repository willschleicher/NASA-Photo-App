package com.example.nasaphotosapp.service;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class DateParserService
{

    public List<String> parseDatesFromFile(String filePath) throws IOException
    {
        List<String> dates = Files.readAllLines(Path.of(filePath));
        List<String> formattedDates = new ArrayList<>();

        for (String date : dates)
        {
            formattedDates.add(parseToFormattedDate(date));
        }

        return formattedDates;
    }

    public String parseToFormattedDate(String date)
    {
        // Remove any leading/trailing spaces
        date = date.trim();

        // List of possible date formats
        List<SimpleDateFormat> dateFormats = List.of(
                new SimpleDateFormat("MM/dd/yy"),
                new SimpleDateFormat("MMMM d, yyyy"),
                new SimpleDateFormat("MMM-dd-yyyy"),
                new SimpleDateFormat("MMMM dd, yyyy")
        );

        // Try to parse with each format
        for (SimpleDateFormat format : dateFormats)
        {
            try
            {
                Date parsedDate = format.parse(date);
                // Return in the format expected by NASA API (YYYY-MM-DD)
                return new SimpleDateFormat("yyyy-MM-dd").format(parsedDate);
            } catch (ParseException ignored)
            {
                // Try next format
            }
        }

        throw new IllegalArgumentException("Unable to parse date: " + date);
    }
}
