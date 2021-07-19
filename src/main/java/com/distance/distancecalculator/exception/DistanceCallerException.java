package com.distance.distancecalculator.exception;

public class DistanceCallerException extends RuntimeException {

    public DistanceCallerException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }

}
