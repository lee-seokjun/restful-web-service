package com.example.restfulwebservice.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Post {
    @Id
    @GeneratedValue
    private Integer id;

    private String description;
    //User : Post -> 1: (0~N)  , main 대 sub -> Parent : Child
    //관계설정
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private User user;


}
