package com.moviedekho.movieservice.controller;

import com.moviedekho.movieservice.document.MovieDocument;
import com.moviedekho.movieservice.model.request.MovieRequest;
import com.moviedekho.movieservice.model.response.GenericResponse;
import com.moviedekho.movieservice.model.response.MovieResponse;
import com.moviedekho.movieservice.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/movie")
@CrossOrigin(origins = {"http://localhost:4200", "*"})
public class MovieController {

    private final MovieService movieService;

    @Autowired
    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping("/getAllMovies")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<MovieDocument>> getAllMovies() {
        List<MovieDocument> movies = movieService.getAllMovies();;
        return new ResponseEntity<>(movies, HttpStatus.OK);
    }


    @PostMapping("/addMovieDetails")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<MovieResponse> createMovie(@RequestBody MovieRequest movie) {
        MovieResponse savedMovie = movieService.addMovie(movie);
        return new ResponseEntity<>(savedMovie, HttpStatus.CREATED);
    }

    @PutMapping("/updateMovieDetails")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<MovieResponse> updateMovie(@RequestBody MovieRequest movie) throws Exception {

        MovieResponse updatedMovieDetails = movieService.updateMovie(movie);
        return ResponseEntity.ok(updatedMovieDetails);

    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<GenericResponse> deleteMovie(@PathVariable String id) throws Exception {

        GenericResponse genericResponse =  movieService.deleteMovie(id);
        return ResponseEntity.ok(genericResponse);


    }

    @GetMapping("/searchMovies")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<MovieDocument>> searchMovies(
            @RequestParam(value = "genre", required = false) String genre,
            @RequestParam(value = "rating", required = false) String rating,
            @RequestParam(value = "actor", required = false) String actor,
            @RequestParam(value = "yearOfRelease", required = false) Integer yearOfRelease,
            @RequestParam(value = "title", required = false) String title
    ) {

        List<MovieDocument> movies = movieService.searchByCriteria(genre, rating, actor, yearOfRelease, title);
        if (movies == null || movies.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.emptyList());
        }
        return ResponseEntity.ok(movies);
    }

}
