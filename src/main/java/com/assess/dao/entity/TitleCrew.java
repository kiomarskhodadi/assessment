package com.assess.dao.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "TITLE_CREW")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TitleCrew {
    @Id
    @Column(name = "TCONST")
    private String  tconst;
    @Column(name = "DIRECTORS")
    private String directors;
    @Column(name = "WRITERS")
    private String writers;
}
