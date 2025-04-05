package com.assess.dao.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "TITLE_RATINGS",schema = "ASSESSMENT_DATA")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TitleRatings {
    @Id
    @Column(name = "TCONST")
    private String  tconst;
    @Column(name = "AVERAGE_RATING")
    private Float averageRating;
    @Column(name = "NUM_VOTES")
    private Integer numVotes;
}
