package com.devsuperior.movieflix.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.movieflix.dto.MovieCardDTO;
import com.devsuperior.movieflix.dto.MovieDetailsDTO;
import com.devsuperior.movieflix.entities.Movie;
import com.devsuperior.movieflix.repositories.MovieRepository;
import com.devsuperior.movieflix.services.exceptions.ResourceNotFoundException;

@Service
public class MovieService {
	
	@Autowired
	private MovieRepository repository;

	@Transactional(readOnly = true)
	public Page<MovieCardDTO> findAllByGenre(Long genreId, Pageable pageable) {
		Page<Movie> result = repository.searchMoviesByGenre(genreId, pageable);
		return result.map(x -> new MovieCardDTO(x));
	}
	
	@Transactional(readOnly = true)
	public Page<MovieCardDTO> findAll(Pageable pageable) {
		Page<Movie> result = repository.searchAll(pageable);
		return result.map(x -> new MovieCardDTO(x));
	}
	
	@Transactional(readOnly = true)
	public MovieDetailsDTO findById(Long id) {
		try {
			Movie entity = repository.findById(id)
					.orElseThrow(()-> new ResourceNotFoundException("Movie not found"));
			return new MovieDetailsDTO(entity);
		}catch (ResourceNotFoundException e) {
			throw new ResourceNotFoundException("Movie not found");
		}
	}

}
