package com.assess.config.writer;

import com.assess.dao.entity.TitleBasics;
import com.assess.service.dto.TitleGenreDto;
import com.assess.service.dto.TitleGenresDto;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Objects;

public class TitleBaseGenresWriters implements ItemWriter<TitleGenresDto> {

    private static final String SQL = " INSERT INTO title_basics (         " +
                                      "   TCONST,                          " +
                                      "   TITLE_TYPE,                      " +
                                      "   PRIMARY_TITLE,                   " +
                                      "   ORIGINAL_TITLE,                  " +
                                      "   IS_ADULT,                        " +
                                      "   START_YEAR,                      " +
                                      "   END_YEAR,                        " +
                                      "   RUNTIME_MINUTES,                 " +
                                      "   TITLE_GENRES                     " +
                                      "   )                                " +
                                      " values (?,?,?,?,?,?,?,?,?);        ";

    private static final String SQL_GENRES =
                                      " INSERT INTO title_genres (         " +
                                      "   tconst,                          " +
                                      "   genres                           " +
                                      " )                                  " +
                                      " values (?,?);                      ";

    @Autowired
    private DataSource dataSource;


    @Override
    public void write(Chunk<? extends TitleGenresDto> items) throws Exception {

        try(Connection con = dataSource.getConnection();
            PreparedStatement preparedStatement = con.prepareStatement(SQL)){
            TitleBasics titleBasic;
            for(TitleGenresDto item:items.getItems()){
                titleBasic = item.getTitleBasics();
                if(Objects.nonNull(titleBasic)){
                    preparedStatement.setString(1, titleBasic.getTconst());
                    preparedStatement.setString(2, titleBasic.getTitleType());
                    preparedStatement.setString(3, "");
                    preparedStatement.setString(4, "");
                    preparedStatement.setString(5, titleBasic.getIsAdult());
                    preparedStatement.setInt(6, Objects.isNull(titleBasic.getStartYear())? -1 :titleBasic.getStartYear() );
                    preparedStatement.setInt(7, Objects.isNull(titleBasic.getEndYear())? -1 :titleBasic.getEndYear() );
                    preparedStatement.setInt(8, Objects.isNull(titleBasic.getRuntimeMinutes())? -1 :titleBasic.getRuntimeMinutes() );
                    preparedStatement.setString(9, titleBasic.getTitleGenres());
                    preparedStatement.setString(9, "");
                    preparedStatement.addBatch();
                }
            }
            preparedStatement.executeBatch();
        }

        try(Connection con = dataSource.getConnection();
            PreparedStatement preparedStatement = con.prepareStatement(SQL_GENRES)){
            for(TitleGenresDto item:items.getItems()){
                for(TitleGenreDto titleGenreDto :item.getTitleGenres()){
                    preparedStatement.setString(1, titleGenreDto.getTconst());
                    preparedStatement.setString(2, titleGenreDto.getGenres());
                    preparedStatement.addBatch();
                }
            }
            preparedStatement.executeBatch();
        }
    }
}
