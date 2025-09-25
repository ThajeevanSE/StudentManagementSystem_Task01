package com.example.studentManagement.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student {
    private String id;
    private String title;
    private String name;
    private String address;
    private String city;
    private String course;
}
