package com.vaccineadminsystem.controller;

import com.vaccineadminsystem.dto.MessageRes;
import com.vaccineadminsystem.dto.NewDetailDto;
import com.vaccineadminsystem.dto.NewsDto;
import com.vaccineadminsystem.entity.News;
import com.vaccineadminsystem.exception.ConstraintException;
import com.vaccineadminsystem.exception.InputNewsInvalidException;
import com.vaccineadminsystem.service.INewsService;
import com.vaccineadminsystem.util.ErrorMess;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
public class NewsController {
	
	private  Logger log = LoggerFactory.getLogger(NewsController.class);

    @Autowired
    private INewsService newsService;
    
    @GetMapping("/news")
    public ResponseEntity<List<NewsDto>> getListAll() {
		List<NewsDto> listNews = new ArrayList<>(newsService.findListAll());
		return new ResponseEntity<>(listNews, HttpStatus.OK);
	}

    @GetMapping("/news/{id}")
    public ResponseEntity<NewsDto> getNewsByID(@PathVariable String id) {
            NewsDto newsDto = newsService.findNewsById(id);
            if(newsDto != null) {
            	return new ResponseEntity<>(newsDto, HttpStatus.OK);
            }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/news")
    public ResponseEntity<?> addNews(@Valid @RequestBody NewsDto newsDto, BindingResult result) throws InputNewsInvalidException{
        if(result.hasErrors()) {
        	throw new InputNewsInvalidException(ErrorMess.INVALID_NEWS);
        }
        if (newsService.saveNews(newsDto)) {
            return ResponseEntity.ok(new MessageRes(ErrorMess.CREATE_SUCCESS_NEWS));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @PutMapping("/news/{id}")
    public ResponseEntity<NewsDto> updateNews(@Valid @RequestBody NewsDto newsDto, @PathVariable String id, BindingResult result ) throws InputNewsInvalidException {
        	if(result.hasErrors()) {
        		throw new InputNewsInvalidException(ErrorMess.INVALID_NEWS);
            }
        	NewsDto newsDtos = newsService.update(id, newsDto);
            if (newsDtos != null) {
            	return new ResponseEntity<>(newsDtos,HttpStatus.OK);
			}
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/news")
    public ResponseEntity<News> deleteEmployeeById(@RequestParam List<String> ids) throws ConstraintException {
        newsService.deleteByIds(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/news/detail/{id}")
    public ResponseEntity<NewDetailDto> getNewsDetailbyId(@PathVariable String id) {
        NewDetailDto newsDto = newsService.getDetailNew(id);
        if(newsDto != null) {
            return new ResponseEntity<>(newsDto, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
