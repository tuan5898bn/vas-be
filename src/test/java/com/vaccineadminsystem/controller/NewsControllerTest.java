package com.vaccineadminsystem.controller;

import static org.mockito.Mockito.when;
import static org.junit.Assert.assertEquals;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vaccineadminsystem.converter.ConvertNews;
import com.vaccineadminsystem.dto.NewsDto;
import com.vaccineadminsystem.entity.News;
import com.vaccineadminsystem.repository.NewsRepository;
import com.vaccineadminsystem.service.INewsService;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import org.springframework.http.HttpStatus;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import com.vaccineadminsystem.exception.handle.AppExceptionHandle;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.mock.web.MockHttpServletResponse;

import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class NewsControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ModelMapper modelMapper;

    @MockBean
    private NewsRepository newsRepository;

    @MockBean
    private INewsService newsService;

    @Autowired
    private AppExceptionHandle appExceptionHandle;

    @Autowired
    private NewsController newsController;

    @MockBean
    private ConvertNews convertNews;

    News news = null;
    News news1 = null;
    News news2 = null;

    @Before
    public void setUp() {
        Date date = new java.sql.Date(new Date().getTime());
        news1 = new News("newsid1", "Content of news 1", "preview1", "Vaccine 1", date);
        news2 = new News("newsid2", "Content of news 2", "preview2", "Vaccine 2", date);

        List<News> newsList = Arrays.asList(news1,news2);
        when(newsRepository.findAll()).thenReturn(newsList);
        when(newsService.findListAll()).thenReturn(modelMapper.map(newsList, ArrayList.class));
        this.mockMvc =
                MockMvcBuilders.standaloneSetup(newsController)
                        .setControllerAdvice(appExceptionHandle)
                        .build();
    }


    @Test
    public void testFindAllNews() throws Exception{

        mockMvc.perform(get("/news")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].id").value("newsid1"))
                .andExpect(jsonPath("$.[0].content").value("Content of news 1"))
                .andExpect(jsonPath("$.[0].preview").value("preview1"))
                .andExpect(jsonPath("$.[0].title").value("Vaccine 1"));
    }

    @Test
    public void testSaveNewsSuccess() throws Exception{
        Date date = new java.util.Date(new Date().getTime());
        news = new News("newsid3", "Content of news 3", "preview3", "Vaccine 3", date);
       NewsDto newsDto = new NewsDto("newsid3", "newsid3", "preview3", "Vaccine 3", date);
        when(convertNews.converttoDto(news)).thenReturn(newsDto);
        when(newsRepository.save(news)).thenReturn(news);
        final MockHttpServletResponse response = mockMvc.perform(post("/news")
                .content(objectMapper.writeValueAsString(
                modelMapper.map(news, NewsDto.class)))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
        assertEquals(HttpStatus.CREATED.value(), response.getStatus());
        assertEquals("{\"message\":\"Success!\"}",
                response.getContentAsString());

    }

    @Test
    public void testSaveNewsFail() throws Exception {
        NewsDto newsDto = null;
        final MockHttpServletResponse response =
                mockMvc.perform(post("/news")
                        .content(objectMapper.writeValueAsString(newsDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                        .andReturn().getResponse();
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
    }

    @Test
    public void testFindNewsById() throws Exception {
        NewsDto newsDto1 = modelMapper.map(news1, NewsDto.class);
        when(newsRepository.findById(news1.getId())).thenReturn(Optional.of(news1));
        when(newsService.findNewsById(newsDto1.getId())).thenReturn(newsDto1);
        when(convertNews.converttoDto(news1)).thenReturn(newsDto1);

        final MockHttpServletResponse response =
                mockMvc.perform(get("/news/{id}",newsDto1.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                        .andReturn().getResponse();
        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Test
    public void testFindNewsByIdError() throws Exception {
        NewsDto newsDto1 = modelMapper.map(news1, NewsDto.class);
        when(newsRepository.findById(news1.getId())).thenReturn(Optional.of(news1));
        when(newsService.findNewsById(newsDto1.getId())).thenReturn(newsDto1);
        when(convertNews.converttoDto(news1)).thenReturn(newsDto1);

        final MockHttpServletResponse response =
                mockMvc.perform(get("/news/{id}","abc")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                        .andReturn().getResponse();
       assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
    }

    @Test
    public void testDeleteNews() throws Exception {
        when(newsRepository.findById(news1.getId())).thenReturn(Optional.of(news1));
        List<String> ids = new ArrayList<>();
        ids.add(news1.getId());
        when(newsService.deleteByIds(ids)).thenReturn(true);
        final MockHttpServletResponse response =
                mockMvc.perform(delete("/news",ids)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                        .andReturn().getResponse();
        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

}
