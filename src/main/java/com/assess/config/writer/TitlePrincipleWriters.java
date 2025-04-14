package com.assess.config.writer;

import com.assess.service.dto.TitlePrinciplesDto;
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


public class TitlePrincipleWriters implements ItemWriter<TitlePrinciplesDto> {

    private static final String SQL = " INSERT INTO title_principles (        " +
                                      "   tconst,                             " +
                                      "   ordering,                           " +
                                      "   nconst,                             " +
                                      "   principle_category                  " +
//                                      "   job,                                " +
//                                      "   characters                          " +
                                      "   )                                   " +
                                      " values (?,?,?,?);                     ";

    @Autowired
    private DataSource dataSource;

    @Override
    public void write(Chunk<? extends TitlePrinciplesDto> chunk) throws Exception {
        try(Connection con = dataSource.getConnection();
            PreparedStatement preparedStatement = con.prepareStatement(SQL)){
            for (TitlePrinciplesDto titlePrinciples : chunk.getItems()) {
                preparedStatement.setString(1, titlePrinciples.getTconst());
                preparedStatement.setInt(2, Objects.isNull(titlePrinciples.getOrdering())? -1 : titlePrinciples.getOrdering());
                preparedStatement.setString(3, titlePrinciples.getNconst());
                preparedStatement.setString(4, titlePrinciples.getPrincipleCategory());
//                preparedStatement.setString(5, titlePrinciples.getJob());
//                preparedStatement.setString(6, titlePrinciples.getCharacters());
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
