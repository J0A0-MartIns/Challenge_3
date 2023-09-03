package br.com.compass.Challenge_3.dto;

import java.util.List;

public class PostDto {
	
	private Long id;   
	private String title;
    private String body;
    private List<CommentDto> comments;
    private List<HistoryDto> history;	
    
    public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public List<CommentDto> getComments() {
		return comments;
	}
	public void setComments(List<CommentDto> comments) {
		this.comments = comments;
	}
	public List<HistoryDto> getHistory() {
		return history;
	}
	public void setHistory(List<HistoryDto> history) {
		this.history = history;
	}
    
}
