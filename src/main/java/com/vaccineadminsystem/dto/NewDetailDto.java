package com.vaccineadminsystem.dto;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class NewDetailDto {
    @Id
    private String id;
    private String newId;
    private String content;
    private String preview;
    private Date postDate;
    private String newsTypeId;
    private String description;
    private String name;

    public NewDetailDto(String newId, String content, String preview, Date postDate, String newsTypeId, String description, String name) {
        this.newId = newId;
        this.content = content;
        this.preview = preview;
        this.postDate = postDate;
        this.newsTypeId = newsTypeId;
        this.description = description;
        this.name = name;
    }

    public NewDetailDto() {

    }

    public String getNewId() {
        return newId;
    }

    public void setNewId(String newId) {
        this.newId = newId;
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

    public Date getPostDate() {
        return postDate;
    }

    public void setPostDate(Date postDate) {
        this.postDate = postDate;
    }

    public String getNewsTypeId() {
        return newsTypeId;
    }

    public void setNewsTypeId(String newsTypeId) {
        this.newsTypeId = newsTypeId;
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

    public void setId(String id) {
        this.id = id;
    }
}
