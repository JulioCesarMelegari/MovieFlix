package com.devsuperior.movieflix.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.movieflix.dto.ReviewDTO;
import com.devsuperior.movieflix.entities.Movie;
import com.devsuperior.movieflix.entities.Review;
import com.devsuperior.movieflix.entities.User;
import com.devsuperior.movieflix.repositories.MovieRepository;
import com.devsuperior.movieflix.repositories.ReviewRepository;
import com.devsuperior.movieflix.repositories.UserRepository;
import com.devsuperior.movieflix.services.exceptions.MovieNotFoundException;

@Service
public class ReviewService {
	
	@Autowired
	private ReviewRepository reviewRepository;
	
	@Autowired
	private MovieRepository movieRepository;
	
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserService userService;
	
	@Transactional
	public ReviewDTO insert(ReviewDTO dto){
		Review entity = new Review();
		
		if (!movieRepository.existsById(dto.getMovieId())) {
			throw new MovieNotFoundException("Movie not found");
		}else {
		Movie movie = movieRepository.findById(dto.getMovieId()).get();
		entity.setMovie(movie);
		entity.setText(dto.getText());
		
		Long idUser = userService.getMe().getId();
		
		User user = userRepository.findById(idUser).get();
		
		entity.setUser(user);
		
		entity = reviewRepository.save(entity);
		return new ReviewDTO(entity);
		}
	}
	
}
