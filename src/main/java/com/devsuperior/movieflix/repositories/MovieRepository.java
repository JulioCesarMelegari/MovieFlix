package com.devsuperior.movieflix.repositories;



import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.devsuperior.movieflix.entities.Movie;

public interface MovieRepository extends JpaRepository<Movie, Long> {
	
	@Query(
	        nativeQuery = true, 
	        value = """
	            SELECT * 
	            FROM tb_movie 
	            WHERE genre_id = :genreId
	            """,
	        countQuery = """
	            SELECT COUNT(*) 
	            FROM tb_movie 
	        	WHERE genre_id = :genreId
	            """
	    )
	    Page<Movie> searchMoviesByGenre(Long genreId, Pageable pageable);

	@Query(
	        nativeQuery = true, 
	        value = """
	            SELECT * 
	            FROM tb_movie ORDER BY title
	            """,
	        countQuery = """
	            SELECT COUNT(*) 
	            FROM tb_movie ORDER BY title
	            """
	    )
	Page<Movie> searchAll(Pageable pageable);
}
