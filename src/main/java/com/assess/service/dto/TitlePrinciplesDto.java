package com.assess.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class TitlePrinciplesDto {
    private String  tconst;
    private Integer ordering;
    private String  nconst;
    private String principleCategory;
    private String job;
    private String characters;
}
