package com.openclassrooms.poseidon.domain;

import jakarta.persistence.*;
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

    String moodysRating;

    String sandPRating;

    String fitchRating;

    Integer orderNumber;

    @Override
    public void update(Rating rating) {
        this.moodysRating = rating.moodysRating;
        this.sandPRating = rating.sandPRating;
        this.fitchRating = rating.fitchRating;
        this.orderNumber = rating.orderNumber;
    }
}
