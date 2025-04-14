package com.assess.dao.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "TITLE_PRINCIPLES", indexes = {@Index(name = "idx_title_principles_tconst ", columnList = "tconst"),
                                            @Index(name = "idx_title_principles_nconst ", columnList = "nconst")})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TitlePrinciples {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TITLE_PRINCIPLES_seq")
    @SequenceGenerator(name = "TITLE_PRINCIPLES_seq", sequenceName = "TITLE_PRINCIPLES_seq", allocationSize = 100)
    @Column(name = "Title_Principles_Id")
    private Integer  TitlePrinciplesId;
    @Column(name = "TCONST")
    private String  tconst;
    @Column(name = "ORDERING")
    private Integer ordering;
    @Column(name = "NCONST")
    private String  nconst;
    @Column(name = "PRINCIPLE_CATEGORY")
    private String principleCategory;
    @Column(name = "JOB")
    private String job;
    @Column(name = "CHARACTERS")
    private String characters;
}
