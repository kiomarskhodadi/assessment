package com.assess.config.writer;

import com.assess.dao.entity.*;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.stream.Collectors;

public class TitleDirectorsWritersWriters implements ItemWriter<TitleDirectorsWritersProcessor> {

    private static final String SQL_DIRECTORS =
                                    " INSERT INTO title_directors (    " +
                                    "   tconst,                          " +
                                    "   director                         " +
                                    "   )                                " +
                                    " values (?,?);                      ";

    private static final String SQL_WRITERS =
                                      " INSERT INTO title_writers (        " +
                                      "   tconst,                          " +
                                      "   writer                           " +
                                      " )                                  " +
                                      " values (?,?);                      ";

    @Autowired
    private DataSource dataSource;

    @Override
    public void write(Chunk<? extends TitleDirectorsWritersProcessor> items) throws Exception {
        try(Connection con = dataSource.getConnection();
            PreparedStatement preparedStatement = con.prepareStatement(SQL_DIRECTORS)){
            for(TitleDirectorsWritersProcessor item:items.getItems()){
                for(TitleDirectors titleDirectors:item.getTitleDirectors()){
                    preparedStatement.setString(1, titleDirectors.getTconst());
                    preparedStatement.setString(2, titleDirectors.getDirector());
                    preparedStatement.addBatch();
                }
            }
            preparedStatement.executeBatch();
        }
        try(Connection con = dataSource.getConnection();
            PreparedStatement preparedStatement = con.prepareStatement(SQL_WRITERS)){
            for(TitleDirectorsWritersProcessor item:items.getItems()){
                for(TitleWriters titleWriters:item.getTitleWriters()){
                    preparedStatement.setString(1, titleWriters.getTconst());
                    preparedStatement.setString(2, titleWriters.getWriter());
                    preparedStatement.addBatch();
                }
            }
            preparedStatement.executeBatch();
        }
    }


}
