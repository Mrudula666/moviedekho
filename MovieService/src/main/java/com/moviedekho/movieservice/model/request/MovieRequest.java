package com.moviedekho.movieservice.model.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MovieRequest {


    private String title;
    private List<String> actors;
    private String genre;
    private Integer yearOfRelease;
    private String rating;
    private String streamLink;
    private String moviePoster;

}
