package com.moviebooking.controller;

import com.moviebooking.model.Movie;
import com.moviebooking.repository.MovieRepository;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/movies")
public class MovieController {

    private final MovieRepository movieRepository;

    public MovieController(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    // Get all movies
    @GetMapping
    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    // Get movie by ID
    @GetMapping("/{id}")
    public Movie getMovieById(@PathVariable Long id) {
        return movieRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Movie not found"));
    }

    // Add movie
    @PostMapping
    public Movie addMovie(@RequestBody Movie movie) {
        return movieRepository.save(movie);
    }

    // Update movie
    @PutMapping("/{id}")
    public Movie updateMovie(
            @PathVariable Long id,
            @RequestBody Movie movie) {

        Movie existingMovie = movieRepository
                .findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Movie not found"));

        existingMovie.setTitle(movie.getTitle());
        existingMovie.setLanguage(movie.getLanguage());
        existingMovie.setGenre(movie.getGenre());
        existingMovie.setDuration(movie.getDuration());
        existingMovie.setPrice(movie.getPrice());

        return movieRepository.save(existingMovie);
    }

    // Delete movie
    @DeleteMapping("/{id}")
    public String deleteMovie(@PathVariable Long id) {

        movieRepository.deleteById(id);

        return "Movie deleted successfully";
    }
}
