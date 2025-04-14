package com.assess.config.writer;

import com.assess.dao.entity.TitleRatings;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Objects;

/**
 * @Creator 4/14/2025
 * @Project IntelliJ IDEA
 * @Author k.khodadi
 **/


public class TitleRatingWriters implements ItemWriter<TitleRatings> {

    private static final String SQL = " INSERT INTO title_ratings (           " +
                                      "   tconst,                             " +
                                      "   average_rating,                     " +
                                      "   num_votes                           " +
                                      "   )                                   " +
                                      " values (?,?,?);                       ";

    @Autowired
    private DataSource dataSource;

    @Override
    public void write(Chunk<? extends TitleRatings> chunk) throws Exception {
        try(Connection con = dataSource.getConnection();
            PreparedStatement preparedStatement = con.prepareStatement(SQL)){
            for (TitleRatings TitleRating : chunk.getItems()) {
                preparedStatement.setString(1, TitleRating.getTconst());
                preparedStatement.setFloat(2, Objects.isNull(TitleRating.getAverageRating())? -1F : TitleRating.getAverageRating());
                preparedStatement.setInt(3, Objects.isNull(TitleRating.getNumVotes())? -1 : TitleRating.getNumVotes());
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
