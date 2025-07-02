package com.openclassrooms.poseidon.services;

import com.openclassrooms.poseidon.domain.Rating;
import com.openclassrooms.poseidon.exceptions.EntityNotFoundException;
import com.openclassrooms.poseidon.repositories.RatingRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class RatingService extends AbstractCrudService<Rating> {

    private final RatingRepository ratingRepository;

    public RatingService(RatingRepository repository) {
        super(repository);
        this.ratingRepository = repository;
    }

    @Override
    public Integer create(Rating rating) {
        Assert.isNull(rating.getId(), "ID needs to be null");
        validateFields(rating);

        Rating savedRating = ratingRepository.save(rating);
        return savedRating.getId();
    }

    @Override
    public void update(Rating rating, Integer id) {
        Rating ratingToUpdate = repository.findById(id).orElseThrow(EntityNotFoundException::new);

        validateFields(rating);

        ratingToUpdate.update(rating);

        ratingRepository.save(ratingToUpdate);
    }

    private void validateFields(Rating rating) {
        Assert.notNull(rating, "Object must not be null");
        Assert.hasLength(rating.getMoodysRating(), "MoodysRating must not be empty");
        Assert.hasLength(rating.getSandPRating(), "SandPRating must not be empty");
        Assert.hasLength(rating.getFitchRating(), "fitchRating must not be empty");
        Assert.isTrue(rating.getOrder() > 0, "Order must be positive");
    }

}
