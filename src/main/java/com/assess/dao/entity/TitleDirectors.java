package com.assess.dao.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Title_Directors", indexes = @Index(name = "idx_title_directors_tconst ", columnList = "tconst"))
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TitleDirectors {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Title_Directors_seq")
    @SequenceGenerator(name = "Title_Directors_seq", sequenceName = "Title_Directors_seq", allocationSize = 100)
    @Column(name = "TITLE_DIRECTORS_ID")
    private Integer titleDirectorsId;
    @Column(name = "TCONST")
    private String  tconst;
    @Column(name = "DIRECTOR")
    private String director;
}
