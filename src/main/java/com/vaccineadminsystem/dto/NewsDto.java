package com.vaccineadminsystem.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

public class NewsDto {
	private String id;

	@NotNull(message = " Content cannot be null")
	@NotEmpty(message = "Content cannot be empty")
	private String content;

	@NotNull(message = "Preview cannot be null")
	@NotEmpty(message = "Preview cannot be empty")
	@Size(min = 1,max = 4000)
    private String preview;

	@NotNull(message = "Title cannot be null")
	@NotEmpty(message = "Title cannot be empty")
    @Size(min = 1,max = 300)
	private String title;

    private Date postDate;

	public NewsDto() {
	}

	public NewsDto(@NotNull(message = " Content cannot be null") @NotEmpty(message = "Content cannot be empty") String content, @NotNull(message = "Preview cannot be null") @NotEmpty(message = "Preview cannot be empty") @Size(min = 1, max = 4000) String preview, @NotNull(message = "Title cannot be null") @NotEmpty(message = "Title cannot be empty") @Size(min = 1, max = 300) String title) {
		this.content = content;
		this.preview = preview;
		this.title = title;
	}

	public NewsDto(String id, @NotNull(message = " Content cannot be null") @NotEmpty(message = "Content cannot be empty") String content, @NotNull(message = "Preview cannot be null") @NotEmpty(message = "Preview cannot be empty") @Size(min = 1, max = 4000) String preview, @NotNull(message = "Title cannot be null") @NotEmpty(message = "Title cannot be empty") @Size(min = 1, max = 300) String title, Date postDate) {
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
}
