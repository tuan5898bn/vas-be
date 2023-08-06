package com.vaccineadminsystem.service;

import com.vaccineadminsystem.dto.NewDetailDto;
import com.vaccineadminsystem.dto.NewsDto;
import com.vaccineadminsystem.entity.News;
import com.vaccineadminsystem.exception.ConstraintException;

import java.util.List;
import java.util.Optional;

public interface INewsService {
	
	public List<NewsDto> findListAll();
	
	public boolean saveNews(NewsDto newsDto);
	
	public NewsDto findNewsById(String id);

	public NewDetailDto getDetailNew(String id);
	
	NewsDto update(String newsId,NewsDto newsDto);
	
	boolean deleteByIds(List<String> ids) throws ConstraintException;
}
