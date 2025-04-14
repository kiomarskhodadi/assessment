package com.assess.dao.repository;

import com.assess.dao.entity.TitleDirectors;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ITitleDirectorsRepo extends JpaRepository<TitleDirectors,Integer> {
}
