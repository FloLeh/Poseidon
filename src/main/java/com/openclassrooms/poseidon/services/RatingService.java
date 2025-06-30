package com.openclassrooms.poseidon.services;

import com.openclassrooms.poseidon.domain.Rating;
import com.openclassrooms.poseidon.repositories.RatingRepository;
import org.springframework.stereotype.Service;

@Service
public class RatingService extends AbstractCrudService<Rating> {

    public RatingService(RatingRepository repository) {
        super(repository);
    }

}
