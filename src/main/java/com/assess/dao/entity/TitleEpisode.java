package com.assess.dao.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "TITLE_EPISODE",schema = "ASSESSMENT_DATA")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TitleEpisode {
    @Id
    @Column(name = "TCONST")
    private String  tconst;
    @Column(name = "PARENT_TCONST")
    private String  parentTconst;
    @Column(name = "SEASON_NUMBER")
    private Integer seasonNumber;
    @Column(name = "EPISODE_NUMBER")
    private Integer episodeNumber;
}
