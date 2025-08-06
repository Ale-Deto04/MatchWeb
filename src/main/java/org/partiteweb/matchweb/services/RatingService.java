package org.partiteweb.matchweb.services;

import org.partiteweb.matchweb.classes.Rating;
import org.partiteweb.matchweb.repositories.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class RatingService {

    RatingRepository ratingRepository;

    @Autowired
    RatingService(RatingRepository ratingRepository) {
        this.ratingRepository = ratingRepository;
    }

    public List<Rating> getRatings() {
        List<Rating> ratings = ratingRepository.getRatings();
        Collections.reverse(ratings); //From the most recent to the oldest
        return ratings;
    }
}
