package com.assess.config.writer;

import com.assess.dao.entity.NameBasics;
import com.assess.dao.entity.TitleBasics;
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


public class NameBasicsItemWriters implements ItemWriter<NameBasics> {

    private static final String SQL = " INSERT INTO name_basics (   " +
                                      "   birth_year,               " +
                                      "   death_year,               " +
                                      "   nconst,                   " +
                                      "   known_for_title,          " +
                                      "   primary_name,             " +
                                      "   primary_profession        " +
                                      "   )                         " +
                                      " values (?,?,?,?,?,?);       ";
    private static final String SQL_S = " INSERT INTO name_basics (   " +
                                      "   birth_year,               " +
                                      "   death_year,               " +
                                      "   nconst                    " +
                                      "   )                         " +
                                      " values (?,?,?);             ";
    @Autowired
    private DataSource dataSource;

    @Override
    public void write(Chunk<? extends NameBasics> chunk) throws Exception {

        try(Connection con = dataSource.getConnection();
            PreparedStatement preparedStatement = con.prepareStatement(SQL_S)){
            for (NameBasics nameBasics : chunk.getItems()) {
                preparedStatement.setInt(1, Objects.isNull(nameBasics.getBirthYear())? -1 : nameBasics.getBirthYear());
                preparedStatement.setInt(2, Objects.isNull(nameBasics.getDeathYear())? -1 : nameBasics.getDeathYear());
                preparedStatement.setString(3, nameBasics.getNconst());
//                preparedStatement.setString(4, nameBasics.getKnownForTitle());
//                preparedStatement.setString(5, nameBasics.getPrimaryName());
//                preparedStatement.setString(6, nameBasics.getPrimaryProfession());
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
        }
    }
}
