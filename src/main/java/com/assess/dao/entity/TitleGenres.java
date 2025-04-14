package com.assess.dao.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Creator 4/9/2025
 * @Project IntelliJ IDEA
 * @Author k.khodadi
 **/

@Entity
@Table(name = "TITLE_GENRES" ,indexes = @Index(name = "idx_title_genres_tconst ", columnList = "tconst"))
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TitleGenres {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "title_genres_seq")
    @SequenceGenerator(name = "title_genres_seq", sequenceName = "title_genres_seq", allocationSize = 100)
    @Column(name = "title_genres_id")
    private Integer titleGenresId;
    private String tconst;
    private String genres;

}
