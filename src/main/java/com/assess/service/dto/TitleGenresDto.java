package com.assess.service.dto;

import com.assess.dao.entity.TitleBasics;

import java.util.List;

public class TitleGenresDto {

    private final TitleBasics titleBasics;
    private final List<TitleGenreDto> titleGenres;

    public TitleGenresDto(TitleBasics titleBasics, List<TitleGenreDto> titleGenres) {
        this.titleBasics = titleBasics;
        this.titleGenres = titleGenres;
    }

    public TitleBasics getTitleBasics() {
        return titleBasics;
    }

    public List<TitleGenreDto> getTitleGenres() {
        return titleGenres;
    }
}
