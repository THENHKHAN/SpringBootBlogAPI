package com.springboot.blogApp.payload;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import java.time.ZonedDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ErrorDetails {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss",  timezone = "Asia/Kolkata") // for customize the date and time. For this i have used a dependency(jackson) in pom.xml
    private ZonedDateTime timeStamp;
    private String message; // will cover error msg
    private String details;
    private int statusCode;
}
