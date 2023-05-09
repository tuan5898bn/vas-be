package com.vaccineadminsystem.service.imp;

import com.vaccineadminsystem.converter.ConvertNews;
import com.vaccineadminsystem.dto.NewsDto;
import com.vaccineadminsystem.entity.News;
import com.vaccineadminsystem.exception.ConstraintException;
import com.vaccineadminsystem.repository.NewsRepository;
import com.vaccineadminsystem.service.INewsService;
import com.vaccineadminsystem.util.ErrorMess;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


/**
 * @author Ngo Van Tuan
 *
 */
@Service
@Transactional
public class NewsServiceImpl implements INewsService {


    @Autowired
    private NewsRepository newsRepository;

    @Autowired
    private ConvertNews convertNews;

    @SuppressWarnings("null")
    @Override
    public List<NewsDto> findListAll() {
        List<News> news = newsRepository.findAll();
        List<NewsDto> newsDtos = new ArrayList<>();
        for (News news2 : news) {
            NewsDto newsDto = convertNews.converttoDto(news2);
            newsDtos.add(newsDto);
        }
        return newsDtos;
    }

    /**
     * @param newsDto info of object NewsDto need create
     * return true in case success or false in case not success
     *
     */
    @Override
    public boolean saveNews(NewsDto newsDto) {
        News checkNews = null;
        try {
            News news = convertNews.convertToEntity(newsDto);
            news.setPostDate(new Date(new java.util.Date().getTime()));
            news.setId(UUID.randomUUID().toString());
            checkNews = newsRepository.save(news);
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        return checkNews != null;
    }

    
    /**
     * @param id of News need get from database
     * @return object News has id input
     *
     */
    @Override
    public NewsDto findNewsById(String id) {
    	Optional<News> news = newsRepository.findById(id);
        return news.map(value -> convertNews.converttoDto(value)).orElse(null);
    }

	/**
	 * @param newsDto info of object NewsDto need update
	 * @param newsId id of News that need to update
	 * @return object News 
	 *
	 */
	@Override
	public NewsDto update(String newsId, NewsDto newsDto) {
		Optional<News> newsUpdate = newsRepository.findById(newsId);
        if (newsUpdate.isPresent()) {
        	newsUpdate.get().setContent(newsDto.getContent());
            newsUpdate.get().setPreview(newsDto.getPreview());
            newsUpdate.get().setTitle(newsDto.getTitle());
            return convertNews.converttoDto(newsRepository.save(newsUpdate.get()));  
        }
        return null;
	}

	@Override
	public boolean deleteByIds(List<String> ids) throws ConstraintException {
		try {
			newsRepository.deleteNewsWithIds(ids);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ConstraintException(ErrorMess.NEWS_CONSTRAINT);
        }
	}
}
