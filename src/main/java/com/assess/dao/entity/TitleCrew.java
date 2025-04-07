package com.assess.dao.entity;

import com.assess.dao.convertors.StringListConverter;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import java.util.ArrayList;
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
    @Convert(converter = StringListConverter.class)
    private List<String> directors;
    @Column(name = "WRITERS")
    @Convert(converter = StringListConverter.class)
    private List<String> writers;
}
