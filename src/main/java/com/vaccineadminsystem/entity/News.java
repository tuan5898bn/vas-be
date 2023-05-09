package com.vaccineadminsystem.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "NEWS")
public class News {
    @Id
    @Column(name = "NEWS_ID")
    private String id;
   
    @Column(name = "CONTENT")
    private String content;
  
    @Column(name = "PREVIEW")
    private String preview;

    @Column(name = "TITLE")
    private String title;
    
    @Column(name = "POSTDATE")
    private Date postDate;
    
    @ManyToOne
    @JoinColumn(name = "NEWS_TYPE_ID", referencedColumnName = "NEWS_TYPE_ID")
    private NewsType newsType;
    
	public News() {
		
    }

	public News(String content, String preview, String title, Date postDate) {
        super();
        this.content = content;
        this.preview = preview;
        this.title = title;
        this.postDate = postDate;
    }

    public News(String id, String content, String preview, String title, Date postDate) {
        this.id = id;
        this.content = content;
        this.preview = preview;
        this.title = title;
        this.postDate = postDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPreview() {
        return preview;
    }

    public void setPreview(String preview) {
        this.preview = preview;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getPostDate() {
        return postDate;
    }

    public void setPostDate(Date postDate) {
        this.postDate = postDate;
    }

    public NewsType getNewsType() {
        return newsType;
    }

    public void setNewsType(NewsType newsType) {
        this.newsType = newsType;
    }
}