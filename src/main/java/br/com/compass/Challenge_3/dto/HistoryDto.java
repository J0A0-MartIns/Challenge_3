package br.com.compass.Challenge_3.dto;

import java.util.Date;

public class HistoryDto {
    private Long id;
    private Date date;
    private String state;
	
    public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}    
}