package com.assess.service.dto;

import java.util.List;

public class TitleDirectorsWritersDto {
    private final List<TitleWritersDto> titleWriterDtos;
    private final List<TitleDirectorsDto> titleDirectorDtos;

    public TitleDirectorsWritersDto(List<TitleWritersDto> titleWriterDtos, List<TitleDirectorsDto> titleDirectorDtos) {
        this.titleWriterDtos = titleWriterDtos;
        this.titleDirectorDtos = titleDirectorDtos;
    }

    public List<TitleWritersDto> getTitleWriters() {
        return titleWriterDtos;
    }

    public List<TitleDirectorsDto> getTitleDirectors() {
        return titleDirectorDtos;
    }
}
