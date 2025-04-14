package com.assess.config.writer;

import com.assess.service.dto.TitleDirectorsWritersDto;
import com.assess.service.dto.TitleDirectorsDto;
import com.assess.service.dto.TitleWritersDto;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class TitleDirectorsWritersWriters implements ItemWriter<TitleDirectorsWritersDto> {

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
    public void write(Chunk<? extends TitleDirectorsWritersDto> items) throws Exception {
        try(Connection con = dataSource.getConnection();
            PreparedStatement preparedStatement = con.prepareStatement(SQL_DIRECTORS)){
            for(TitleDirectorsWritersDto item:items.getItems()){
                for(TitleDirectorsDto titleDirectorsDto :item.getTitleDirectors()){
                    preparedStatement.setString(1, titleDirectorsDto.getTconst());
                    preparedStatement.setString(2, titleDirectorsDto.getDirector());
                    preparedStatement.addBatch();
                }
            }
            preparedStatement.executeBatch();
        }
        try(Connection con = dataSource.getConnection();
            PreparedStatement preparedStatement = con.prepareStatement(SQL_WRITERS)){
            for(TitleDirectorsWritersDto item:items.getItems()){
                for(TitleWritersDto titleWritersDto :item.getTitleWriters()){
                    preparedStatement.setString(1, titleWritersDto.getTconst());
                    preparedStatement.setString(2, titleWritersDto.getWriter());
                    preparedStatement.addBatch();
                }
            }
            preparedStatement.executeBatch();
        }
    }


}
