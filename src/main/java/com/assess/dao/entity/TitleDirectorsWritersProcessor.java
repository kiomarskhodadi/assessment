package com.assess.dao.entity;

import java.util.List;

public class TitleDirectorsWritersProcessor {
    private final List<TitleWriters> titleWriters;
    private final List<TitleDirectors> titleDirectors;

    public TitleDirectorsWritersProcessor(List<TitleWriters> titleWriters, List<TitleDirectors> titleDirectors) {
        this.titleWriters = titleWriters;
        this.titleDirectors = titleDirectors;
    }

    public List<TitleWriters> getTitleWriters() {
        return titleWriters;
    }

    public List<TitleDirectors> getTitleDirectors() {
        return titleDirectors;
    }
}
