package com.assess.dao.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Title_Directors")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TitleDirectors {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TITLE_DIRECTORS_ID" )
    private Integer titleDirectorsId;
    @Column(name = "TCONST")
    private String  tconst;
    @Column(name = "DIRECTOR")
    private String director;
}
