package com.assess.dao.repository;

import com.assess.dao.entity.TitleWriters;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ITitleWritesRepo extends JpaRepository<TitleWriters,Integer> {
}
