package com.vaccineadminsystem.entity;

import javax.persistence.*;
import java.util.Set;


@Entity
@Table(name = "NEWS_TYPE")
public class NewsType {

    @Id
    @Column(name = "NEWS_TYPE_ID")
    private String id;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "NEWS_TYPE_NAME")
    private String name;

    @OneToMany(mappedBy = "newsType")
    private Set<News> news;

    public NewsType() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<News> getNews() {
        return news;
    }

    public void setNews(Set<News> news) {
        this.news = news;
    }
}
