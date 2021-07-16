package com.example.restfulwebservice.helloworld;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data //lombok 사용
@AllArgsConstructor //생성자
@NoArgsConstructor //디폴트 생성자
public class HelloWorldBean {
    private String message;


    }

