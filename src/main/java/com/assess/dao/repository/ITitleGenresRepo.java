package com.assess.dao.repository;

import com.assess.dao.entity.TitleGenres;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @Creator 4/9/2025
 * @Project IntelliJ IDEA
 * @Author k.khodadi
 **/

@Repository
public interface ITitleGenresRepo extends JpaRepository<TitleGenres,Integer> {
}
