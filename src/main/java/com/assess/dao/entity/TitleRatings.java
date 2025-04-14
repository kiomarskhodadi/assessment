package com.assess.dao.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "TITLE_RATINGS",indexes = @Index(name = "idx_title_rating_tconst ", columnList = "tconst"))
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TitleRatings {
    @Id
    private String  tconst;
    private Float averageRating;
    private Integer numVotes;
}
