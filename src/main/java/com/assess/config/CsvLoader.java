package com.assess.config;

import com.assess.dao.entity.TitleBasics;
import com.assess.dao.repository.ITitleBaseRepo;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;

/**
 * @Creator 4/8/2025
 * @Project IntelliJ IDEA
 * @Author k.khodadi
 **/

@Service
public class CsvLoader implements CommandLineRunner {
    @Autowired
    private ITitleBaseRepo titleBaseRepo;

    @Override
    public void run(String... args) throws Exception {
//        try (Reader reader = new BufferedReader(new InputStreamReader(
//                getClass().getResourceAsStream("/title.basics.tsv")))) {
//
//            CsvToBean<TitleBasics> csvToBean = new CsvToBeanBuilder<TitleBasics>(reader)
//                    .withType(TitleBasics.class)
//                    .withIgnoreLeadingWhiteSpace(true)
//                    .build();
//
//            List<TitleBasics> users = csvToBean.parse();
//            titleBaseRepo.saveAll(users);
//        }catch (Exception e){
//            e.printStackTrace();
//        }
    }
}
