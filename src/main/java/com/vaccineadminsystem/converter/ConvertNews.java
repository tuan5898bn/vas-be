package com.vaccineadminsystem.converter;

import com.vaccineadminsystem.dto.NewsDto;
import com.vaccineadminsystem.entity.News;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ConvertNews {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public NewsDto converttoDto(News news) {
		return modelMapper.map(news, NewsDto.class);
	}
	
	public News convertToEntity(NewsDto newsDto) {
		return modelMapper.map(newsDto, News.class);
	}
}
