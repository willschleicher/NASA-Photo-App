package com.example.nasaphotosapp.exception;

public class NasaApiException extends RuntimeException
{
    public NasaApiException(String message)
    {
        super(message);
    }

    public NasaApiException(String message, Throwable cause)
    {
        super(message, cause);
    }
}
