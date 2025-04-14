package com.assess.dao.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//@Entity
//@Table(name = "TITLE_PRINCIPLES", indexes = {@Index(name = "idx_title_principles_tconst ", columnList = "tconst"),
//                                            @Index(name = "idx_title_principles_nconst ", columnList = "nconst")})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TitlePrinciples {
    private String  tconst;
    private Integer ordering;
    private String  nconst;
    private String principleCategory;
    private String job;
    private String characters;
}
