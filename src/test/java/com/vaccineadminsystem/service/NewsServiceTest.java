package com.vaccineadminsystem.service;

import com.vaccineadminsystem.converter.ConvertNews;
import com.vaccineadminsystem.dto.NewsDto;
import com.vaccineadminsystem.entity.News;
import com.vaccineadminsystem.exception.ConstraintException;
import com.vaccineadminsystem.repository.NewsRepository;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.boot.test.context.SpringBootTest;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class NewsServiceTest {


    @Mock
    private NewsRepository newsRepository1;

    @MockBean
    private NewsRepository newsRepository2;

    @Autowired
    private INewsService newsService;

    @Autowired
    private ConvertNews convertNews;

    News news1 = null;
    News news2 = null;

    @Before
    public void setUp() throws  Exception{
        List<News> news = new ArrayList<>();
        String testDate = "29-Apr-2010,13:00:14 PM";
        DateFormat formatter = new SimpleDateFormat("d-MMM-yyyy");
        Date date = formatter.parse(testDate);
        news1 = new News("newsid1", "Content of news 1", "preview1", "Vaccine 1", date);
        news2 = new News("newsid2", "Content of news 2", "preview2", "Vaccine 2", date);
        when(newsRepository2.save(news1)).thenReturn(news1);
        when(newsRepository2.save(news2)).thenReturn(news2);
        when(newsRepository1.save(news1)).thenReturn(news1);
        when(newsRepository1.save(news2)).thenReturn(news2);
        news.add(news1);
        news.add(news2);
        when(newsRepository2.findAll()).thenReturn(news);
        when(newsRepository1.findAll()).thenReturn(news);
    }

    @Test
    public void testFindNewsByIdSuccess() {
        String newId = "newsid1";
        when(newsRepository2.findById(newId)).thenReturn(Optional.of(news1));
        NewsDto newsDto = newsService.findNewsById(newId);
        assertEquals(news1.getId(), newsDto.getId());
    }

    @Test
    public void testFindNewsByIdEmpty() {
        String newsId = "";
        when(newsRepository1.findById(newsId)).thenReturn(null);
        NewsDto newsDto = newsService.findNewsById(newsId);
        assertNull(newsDto);
    }

    @Test
    public void testFindNewsByIdNotExist() {
        String newsId = "mockNewsId";
        when(newsRepository1.findById(newsId)).thenReturn(null);
        NewsDto newsDto = newsService.findNewsById(newsId);
        assertNull(newsDto);
    }

    @Test
    public void testDeleteNewsByIdSuccess() throws ConstraintException {
        String newsId = "newsid1";
        List<String> ids = new ArrayList<>();
        newsService.deleteByIds(ids);
        verify(newsRepository2).deleteNewsWithIds(ids);
    }

    @Test
    public void testDeleteNewsBymultilId() throws ConstraintException {
        String newsId1 = "newsid1";
        String newsId2 = "newsid2";
        List<String> ids = new ArrayList<>();
        ids.add(newsId1);
        ids.add(newsId2);
        newsService.deleteByIds(ids);
        verify(newsRepository2).deleteNewsWithIds(ids);
    }

    @Test
    public void testFindAllNews() throws ParseException {
        List<News> news = new ArrayList<>();
        String testDate = "29-Apr-2010,13:00:14 PM";
        DateFormat formatter = new SimpleDateFormat("d-MMM-yyyy");
        Date date = formatter.parse(testDate);
        news1 = new News("newsid1", "Content of news 1", "preview1", "Vaccine 1", date);
        news2 = new News("newsid2", "Content of news 2", "preview2", "Vaccine 2", date);
        news.add(news1);
        news.add(news2);
        when(newsRepository1.findAll()).thenReturn(news);
        List<NewsDto> news3 = newsService.findListAll();
        assertEquals(2,news3.size());
    }

    @Test
    public void testSaveNewsSuccess() throws ParseException {
        Date date = new java.sql.Date(new java.util.Date().getTime());
        News news3 = new News("newsid3", "Content of news 3", "", "title", date);
        when(newsRepository2.save(news3)).thenReturn(news3);
        NewsDto newsDto = convertNews.converttoDto(news3);
        assertTrue(newsService.saveNews(newsDto));

    }

    @Test
    public void testSaveNewsIdIsExist() throws ParseException {
        Date date = new java.sql.Date(new java.util.Date().getTime());
        News news = new News("newsid1", "Content of news 3", "", "title", date);
        when(newsRepository1.save(news)).thenReturn(news);
        NewsDto newsDto = convertNews.converttoDto(news);
        assertFalse(newsService.saveNews(newsDto));
    }

    @Test
    public void testSaveNewsPreviewIsNull() throws ParseException {
        Date date = new java.sql.Date(new java.util.Date().getTime());
        News news3 = new News("newsid1", "Content of news 3", "", "Vaccine 3", date);
        when(newsRepository1.save(news3)).thenReturn(news3);
        NewsDto newsDto = convertNews.converttoDto(news3);
        assertFalse(newsService.saveNews(newsDto));
    }

    @Test
    public void testUpdateNewsSuccess() {
        NewsDto newsDto = new NewsDto("aa","bb","cc");
        when(newsRepository2.save(news1)).thenReturn(news1);
        when(newsRepository2.findById("newsid1")).thenReturn(Optional.of(news1));
        System.out.println(news1);
        NewsDto newsDto2 = newsService.update("newsid1", newsDto);
        assertNotNull(newsDto2);
    }

    @Test
    public void testUpdateNewsFail() {
        NewsDto newsDto = new NewsDto("aVaccince 1","Five","Title Vaccine 1");
        when(newsRepository2.save(news1)).thenReturn(news1);
        when(newsRepository2.findById("newsid1")).thenReturn(Optional.of(news1));
        System.out.println(news1);
        NewsDto newsDto2 = newsService.update("newsid3", newsDto);
        assertNull(newsDto2);
    }
}