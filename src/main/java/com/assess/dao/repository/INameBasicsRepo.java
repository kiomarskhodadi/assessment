package com.assess.dao.repository;

import com.assess.dao.entity.NameBasics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface INameBasicsRepo extends JpaRepository<NameBasics,Integer> {
}
