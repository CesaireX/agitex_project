package com.agitex.project.agitex.repository;

import com.agitex.project.agitex.model.client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface clientrepository extends JpaRepository<client, Long> {
    @Query("Select c from client c where c.profession LIKE CONCAT('%',:profession,'%')")
    List<client> getclientbyprofession(@Param("profession") String profession);
}
