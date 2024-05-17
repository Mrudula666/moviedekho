package com.moviedekho.userservie.repository;

import com.moviedekho.userservie.entity.FavoriteMovieEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface FavoriteMovieRepository extends JpaRepository<FavoriteMovieEntity, Long> {
    Boolean existsByFavoriteMovie(String movieTitle);

    List<String> findByUsername(String username);

    @Query("SELECT f.favoriteMovie FROM FavoriteMovieEntity f WHERE f.username = :username")
    List<String> findMovieTitlesByUsername(@Param("username") String username);
}

