package com.assess.dao.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "TITLE_AKAS")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TitleAkas {
    @Id
    @Column(name = "TITLE_ID")
    private String titleId;
    @Id
    @Column(name = "ORDERING")
    private Integer ordering;
    @Column(name = "TITLE")
    private String title;
    @Column(name = "REGION")
    private String region;
    @Column(name = "AKAS_LANGUAGE")
    private String akasLanguage;
    @Column(name = "AKAS_TYPES")
    private String akasTypes;
    @Column(name = "ATTRIBUTES")
    private String attributes;
    @Column(name = "IS_ORIGINAL_TITLE")
    private Integer isOriginalTitle;
}
