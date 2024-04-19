package com.moviedekho.movieservice.serviceimpl;

import com.moviedekho.movieservice.document.MovieDocument;
import com.moviedekho.movieservice.model.request.MovieRequest;
import com.moviedekho.movieservice.model.response.GenericResponse;
import com.moviedekho.movieservice.model.response.MovieResponse;
import com.moviedekho.movieservice.repository.MovieRepository;
import com.moviedekho.movieservice.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MovieServiceImpl implements MovieService {

    @Autowired
    private MovieRepository movieRepository;

    @Override
    public List<MovieDocument> getAllMovies() {
        return movieRepository.findAll();
    }
    @Override
    public MovieDocument getMovieById(String id, MovieRequest movie) throws Exception {
        Optional<MovieDocument> movieOptional = movieRepository.findById(id);
        MovieDocument existingMovie = movieOptional.get();
        existingMovie.setTitle(movie.getTitle());
        existingMovie.setActors(movie.getActors());
        existingMovie.setGenre(movie.getGenre());
        existingMovie.setYearOfRelease(movie.getYearOfRelease());
        existingMovie.setRating(movie.getRating());
        existingMovie.setStreamLink(movie.getStreamLink());
        existingMovie.setMoviePoster(movie.getMoviePoster());
        movieRepository.save(existingMovie);
        return movieOptional.get();
    }

    @Override
    public MovieDocument getMovieById(String id) {
        Optional<MovieDocument> movieOptional = movieRepository.findById(id);
        return movieOptional.orElseGet(MovieDocument::new);
    }

    @Override
    public MovieDocument createMovie(MovieDocument movie) {
        return movieRepository.save(movie);
    }
    @Override
    public Optional<MovieDocument> updateMovie(String id, MovieDocument updatedMovie) {
        return movieRepository.findById(id)
                .map(movie -> {
                    movie.setTitle(updatedMovie.getTitle());
                    movie.setActors(updatedMovie.getActors());
                    movie.setGenre(updatedMovie.getGenre());
                    movie.setYearOfRelease(updatedMovie.getYearOfRelease());
                    movie.setRating(updatedMovie.getRating());
                    movie.setStreamLink(updatedMovie.getStreamLink());
                    movie.setMoviePoster(updatedMovie.getMoviePoster());
                    return movieRepository.save(movie);
                });
    }

    @Override
    public GenericResponse deleteMovie(String id) throws Exception {
        Optional<MovieDocument> existingMovie = movieRepository.findById(id);
        if(existingMovie.isPresent()){
            movieRepository.deleteById(id);
            return new GenericResponse("Movie Details Deleted Successfully");
        }else{
            throw new Exception("Unable To Delete Movie Details...");
        }
    }

    @Override
    public MovieResponse addMovie(MovieRequest movieRequest) {
       MovieDocument  movieDocument =  mapMovieDocument(movieRequest);
        MovieDocument savedDocument;
        try {
            savedDocument = movieRepository.save(movieDocument);
            if (savedDocument != null){
                return getMovieResponse(savedDocument, movieDocument);
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to save movie: " + e.getMessage(), e);
        }
        return null;
    }

    private MovieResponse getMovieResponse(MovieDocument savedDocument, MovieDocument movieDocument) {
        MovieResponse movieResponse = new MovieResponse("Movie Details Added SuccessFully");
        movieResponse.setTitle(savedDocument.getTitle());
        movieResponse.setActors(savedDocument.getActors());
        movieResponse.setRating(savedDocument.getRating());
        movieResponse.setGenre(movieDocument.getGenre());
        movieResponse.setYearOfRelease(movieDocument.getYearOfRelease());
        movieResponse.setStreamLink(movieDocument.getStreamLink());
        movieResponse.setPosterLink(movieResponse.getPosterLink());
        return movieResponse;
    }

    private MovieDocument mapMovieDocument(MovieRequest movieRequest) {

        MovieDocument movieDocument = new MovieDocument();
        movieDocument.setTitle(movieRequest.getTitle());
        movieDocument.setActors(movieRequest.getActors());
        movieDocument.setGenre(movieRequest.getGenre());
        movieDocument.setYearOfRelease(movieRequest.getYearOfRelease());
        movieDocument.setRating(movieRequest.getRating());
        movieDocument.setStreamLink(movieRequest.getStreamLink());
        movieDocument.setMoviePoster(movieRequest.getMoviePoster());

        return  movieDocument;
    }


    @Override
    public List<MovieDocument> searchByGenre(String genre) {
        return movieRepository.findByGenre(genre);
    }

    @Override
    public List<MovieDocument> searchByRating(String rating) {
        return movieRepository.findByRating(rating);
    }

    @Override
    public List<MovieDocument> searchByActor(String actor) {
        return movieRepository.findByActorsContaining(actor);
    }

    @Override
    public List<MovieDocument> searchByYearOfRelease(Integer yearOfRelease) {
        return movieRepository.findByYearOfRelease(yearOfRelease);
    }

    @Override
    public List<MovieDocument> searchByTitle(String title) {
        return movieRepository.findByTitleContainingIgnoreCase(title);
    }
}
