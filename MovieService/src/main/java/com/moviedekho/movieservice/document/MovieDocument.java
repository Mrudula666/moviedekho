package com.moviedekho.movieservice.document;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@Setter
@Document(collection = "movies")
public class MovieDocument {

    @Id
    private String id;

    private String title;
    private String actors;
    private String genre;
    private Integer yearOfRelease;
    private String rating;
    private String streamLink;
    private String moviePoster;


}
