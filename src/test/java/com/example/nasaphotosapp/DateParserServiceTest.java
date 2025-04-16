package com.example.nasaphotosapp;

import com.example.nasaphotosapp.service.DateParserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DateParserServiceTest
{

    private final DateParserService dateParserService = new DateParserService();

    @Test
    void shouldParseMultipleDateFormats()
    {
        assertEquals("2017-02-27", dateParserService.parseToFormattedDate("02/27/17"));
        assertEquals("2018-06-02", dateParserService.parseToFormattedDate("June 2, 2018"));
        assertEquals("2016-07-13", dateParserService.parseToFormattedDate("Jul-13-2016"));
        // April has 30 days, but we can assume the user meant May 1st in this case:
        assertEquals("2018-05-01", dateParserService.parseToFormattedDate("April 31, 2018"));
    }

    @Test
    void shouldThrowExceptionForInvalidDateFormat()
    {
        assertThrows(IllegalArgumentException.class, () ->
                dateParserService.parseToFormattedDate("Invalid Date"));
    }

    @Test
    void shouldParseDatesFromFile(@TempDir Path tempDir) throws IOException
    {
        // Create temp file with test data
        Path dateFile = tempDir.resolve("test-dates.txt");
        Files.write(dateFile, List.of(
                "02/27/17",
                "June 2, 2018",
                "Jul-13-2016",
                "April 31, 2018"
        ));

        List<String> parsedDates = dateParserService.parseDatesFromFile(dateFile.toString());

        assertEquals(4, parsedDates.size());
        assertEquals("2017-02-27", parsedDates.get(0));
        assertEquals("2018-06-02", parsedDates.get(1));
        assertEquals("2016-07-13", parsedDates.get(2));
        // April has 30 days, but we can assume the user meant May 1st in this case:
        assertEquals("2018-05-01", parsedDates.get(3));
    }
}