package com.assess.dao.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//@Entity
//@Table(name = "Title_Writers", indexes = @Index(name = "idx_title_writers_tconst ", columnList = "tconst"))
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TitleWriters  {
//    @Id
//    @Column(name = "TCONST")
    private String  tconst;
//    @Column(name = "WRITER")
    private String writer;
}
