package com.vaccineadminsystem.repository;

import com.vaccineadminsystem.dto.NewDetailDto;
import com.vaccineadminsystem.entity.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface NewsDetailDtoRepo extends JpaRepository<NewDetailDto, String> {

    @Query(value = "select n.news_id as id, n.news_id as newId, n.content as content, n.preview as preview, nt.description as description, nt.news_type_name as name " +
            " from news n join news_type nt on n.news_id = nt.news_type_id and n.news_id = ?1", nativeQuery = true
    )
    Optional<NewDetailDto> getDetailNew(String id);
}
