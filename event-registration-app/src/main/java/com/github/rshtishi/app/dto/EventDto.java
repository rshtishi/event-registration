package com.github.rshtishi.app.dto;

public class EventDto {

	private int id;
	private String name;
	private String place;
	private String startDate;
	private String startTime;
	private String endDate;
	private String endTime;
	
	

	public EventDto() {
		super();
	}

	public EventDto(int id, String name, String place, String startDate, String startTime, String endDate,
			String endTime) {
		super();
		this.id = id;
		this.name = name;
		this.place = place;
		this.startDate = startDate;
		this.startTime = startTime;
		this.endDate = endDate;
		this.endTime = endTime;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	@Override
	public String toString() {
		return "EventDto [id=" + id + ", name=" + name + ", place=" + place + ", startDate=" + startDate
				+ ", startTime=" + startTime + ", endDate=" + endDate + ", endTime=" + endTime + "]";
	}

}
