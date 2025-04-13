package com.assess.dao.entity;

import java.util.List;

public class TitleGenresProcessor {

    private final TitleBasics titleBasics;
    private final List<TitleGenres> titleGenres;

    public TitleGenresProcessor(TitleBasics titleBasics, List<TitleGenres> titleGenres) {
        this.titleBasics = titleBasics;
        this.titleGenres = titleGenres;
    }

    public TitleBasics getTitleBasics() {
        return titleBasics;
    }

    public List<TitleGenres> getTitleGenres() {
        return titleGenres;
    }
}
