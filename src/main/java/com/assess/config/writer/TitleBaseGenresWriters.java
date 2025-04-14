package com.assess.config.writer;

import com.assess.dao.entity.TitleBasics;
import com.assess.dao.entity.TitleGenres;
import com.assess.dao.entity.TitleGenresProcessor;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Objects;

public class TitleBaseGenresWriters implements ItemWriter<TitleGenresProcessor> {

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
    public void write(Chunk<? extends TitleGenresProcessor> items) throws Exception {

        try(Connection con = dataSource.getConnection();
            PreparedStatement preparedStatement = con.prepareStatement(SQL)){
            TitleBasics titleBasic;
            for(TitleGenresProcessor item:items.getItems()){
                titleBasic = item.getTitleBasics();
                if(Objects.nonNull(titleBasic)){
                    preparedStatement.setString(1, titleBasic.getTconst());
                    preparedStatement.setString(2, titleBasic.getTitleType());
                    preparedStatement.setString(3, titleBasic.getPrimaryTitle());
                    preparedStatement.setString(4, titleBasic.getOriginalTitle());
                    preparedStatement.setString(5, titleBasic.getIsAdult());
                    preparedStatement.setInt(6, Objects.isNull(titleBasic.getStartYear())? -1 :titleBasic.getStartYear() );
                    preparedStatement.setInt(7, Objects.isNull(titleBasic.getEndYear())? -1 :titleBasic.getEndYear() );
                    preparedStatement.setInt(8, Objects.isNull(titleBasic.getRuntimeMinutes())? -1 :titleBasic.getRuntimeMinutes() );
                    preparedStatement.setString(9, titleBasic.getTitleGenres());
                    preparedStatement.addBatch();
                }
            }
            preparedStatement.executeBatch();
        }

        try(Connection con = dataSource.getConnection();
            PreparedStatement preparedStatement = con.prepareStatement(SQL_GENRES)){
            for(TitleGenresProcessor item:items.getItems()){
                for(TitleGenres titleGenres:item.getTitleGenres()){
                    preparedStatement.setString(1, titleGenres.getTconst());
                    preparedStatement.setString(2, titleGenres.getGenres());
                    preparedStatement.addBatch();
                }
            }
            preparedStatement.executeBatch();
        }
    }
}
