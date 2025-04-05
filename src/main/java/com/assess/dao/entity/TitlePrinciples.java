package com.assess.dao.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "TITLE_PRINCIPLES",schema = "ASSESSMENT_DATA")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TitlePrinciples {

    @Id
    @Column(name = "TCONST")
    private String  tconst;
    @Id
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
