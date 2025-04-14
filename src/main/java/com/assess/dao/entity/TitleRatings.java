package com.assess.dao.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "TITLE_RATINGS")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TitleRatings {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TITLE_RATINGS_seq")
    @SequenceGenerator(name = "TITLE_RATINGS_seq", sequenceName = "TITLE_RATINGS_seq", allocationSize = 100)
    @Column(name = "Title_Rating_Id")
    private Integer  TitleRatingsId;
    @Column(name = "TCONST")
    private String  tconst;
    @Column(name = "AVERAGE_RATING")
    private Float averageRating;
    @Column(name = "NUM_VOTES")
    private Integer numVotes;
}
