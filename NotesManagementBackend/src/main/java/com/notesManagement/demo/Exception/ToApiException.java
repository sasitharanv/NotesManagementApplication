package com.notesManagement.demo.Exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter

public class ToApiException  extends  RuntimeException{
    private HttpStatus status;
    private  String mesage;
}
