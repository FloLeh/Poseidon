package com.openclassrooms.poseidon.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "Rating")
@AllArgsConstructor
@NoArgsConstructor
public class Rating implements DomainEntity<Rating> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotBlank
    String moodysRating;

    @NotBlank
    String sandPRating;

    @NotBlank
    String fitchRating;

    @Positive
    @Column(name = "orderNumber")
    Integer order;

    public Rating(String moodysRating, String sandPRating, String fitchRating, Integer order) {
        this.moodysRating = moodysRating;
        this.sandPRating = sandPRating;
        this.fitchRating = fitchRating;
        this.order = order;
    }

    @Override
    public void update(Rating rating) {
        this.moodysRating = rating.moodysRating;
        this.sandPRating = rating.sandPRating;
        this.fitchRating = rating.fitchRating;
        this.order = rating.order;
    }
}
