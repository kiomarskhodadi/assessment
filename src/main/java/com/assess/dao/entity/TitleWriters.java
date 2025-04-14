package com.assess.dao.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Title_Writers", indexes = @Index(name = "idx_title_writers_tconst ", columnList = "tconst"))
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TitleWriters  {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Title_Writers_seq")
    @SequenceGenerator(name = "Title_Writers_seq", sequenceName = "Title_Writers_seq", allocationSize = 100)
    @Column(name = "TITLE_DIRECTORS_ID" )
    private Integer titleWritersId;
    @Column(name = "TCONST")
    private String  tconst;
    @Column(name = "WRITER")
    private String writer;
}
