package com.assess.dao.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//@Entity
//@Table(name = "Title_Directors", indexes = @Index(name = "idx_title_directors_tconst ", columnList = "tconst"))
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TitleDirectors {
//    @Id
//    @Column(name = "TCONST")
    private String  tconst;
//    @Column(name = "DIRECTOR")
    private String director;
}
