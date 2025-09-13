package com.devsuperior.movieflix.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.devsuperior.movieflix.dto.MovieCardDTO;
import com.devsuperior.movieflix.dto.MovieDetailsDTO;
import com.devsuperior.movieflix.services.MovieService;

@RestController
@RequestMapping(value = "/movies")
public class MovieController {
	
	@Autowired
	private MovieService service;
	
	@PreAuthorize("hasAnyRole('ROLE_MEMBER', 'ROLE_VISITOR')")
    @GetMapping
    public ResponseEntity<Page<MovieCardDTO>> findAll(@RequestParam(defaultValue = "0") Long genreId,	Pageable pageable) {
		if(genreId == 0) {
			Page<MovieCardDTO> dto = service.findAll(pageable);
			return ResponseEntity.ok(dto);
        }
		Page<MovieCardDTO> dto = service.findAllByGenre(genreId, pageable);
        return ResponseEntity.ok(dto);
    }
	
	@PreAuthorize("hasAnyRole('ROLE_MEMBER', 'ROLE_VISITOR')")
	@GetMapping(value = "/{id}")
    public ResponseEntity<MovieDetailsDTO> update(@PathVariable Long id) {
		MovieDetailsDTO dto = service.findById(id);
        return ResponseEntity.ok(dto);
    }

}
