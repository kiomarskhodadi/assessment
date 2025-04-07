package com.assess.dao.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "NAME_BASICS")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NameBasics {
    @Id
    @Column(name = "NCONST")
    private String nconst;
    @Column(name = "PRIMARY_NAME")
    private String primaryName;
    @Column(name = "BIRTH_YEAR")
    private Integer birthYear;
    @Column(name = "DEATH_YEAR")
    private Integer deathYear;
    @Column(name = "PRIMARY_PROFESSION")
    private String  primaryProfession;
    @Column(name = "KNOWN_FOR_TITLE")
    private String knownForTitle;

}
