package com.vaccineadminsystem.repository;

import com.vaccineadminsystem.entity.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface NewsRepository extends JpaRepository<News, String> {
	
	Optional<News> findById(String id);

	@Modifying
    @Query("delete from News e where e.id in :ids")
    void deleteNewsWithIds(@Param("ids") List<String> ids);
}


