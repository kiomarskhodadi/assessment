package com.assess.dao.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Title_Writers")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TitleWriters  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TITLE_DIRECTORS_ID" )
    private Integer titleWritersId;
    @Column(name = "TCONST")
    private String  tconst;
    @Column(name = "WRITER")
    private String writer;
}
