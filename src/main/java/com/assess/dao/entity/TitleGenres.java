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

//@Entity
//@Table(name = "TITLE_GENRES" ,indexes = @Index(name = "idx_title_genres_tconst ", columnList = "tconst"))
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TitleGenres {

//    @Id
    private String tconst;
    private String genres;

}
