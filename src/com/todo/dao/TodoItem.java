package com.todo.dao;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TodoItem {
	private int id;
    private String title;
    private String desc;
    private String current_date;
    private String category;
    private String due_date;
    private int is_completed;
    private String expected_time;
    private String difficulty;


    public TodoItem(String title, String desc, String current_date, String category, String due_date, String expected_time, String difficulty){
        this.title = title;
        this.desc = desc;
        if(current_date == null) {
	        SimpleDateFormat f = new SimpleDateFormat("yyyy/MM/dd kk:mm:ss");
	        this.current_date = f.format(new Date());
        }
        else this.current_date = current_date;
        this.category = category;
        this.due_date = due_date;
        this.expected_time = expected_time;
        this.difficulty = difficulty;
    }
    
    public TodoItem(String title, String desc, String category, String due_date, String expected_time, String difficulty){
        this.title = title;
        this.desc = desc;
        this.category = category;
        this.due_date = due_date;
	    SimpleDateFormat f = new SimpleDateFormat("yyyy/MM/dd kk:mm:ss");
	    this.current_date = f.format(new Date());
	    this.expected_time = expected_time;
	    this.difficulty = difficulty;
    }
    
    public TodoItem(int id, String title, String desc, String category, String due_date, String current_date, int is_completed, String expected_time, String difficulty) {
    	this.id = id;
    	this.title = title;
    	this.desc = desc;
    	this.category = category;
    	this.due_date = due_date;
    	this.current_date = current_date;
    	this.is_completed = is_completed;
    	this.expected_time = expected_time;
    	this.difficulty = difficulty;
    }
    
    public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getCurrent_date() {
        return current_date;
    }

    public void setCurrent_date(String current_date) {
        this.current_date = current_date;
    }

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getDue_date() {
		return due_date;
	}

	public void setDue_date(String due_date) {
		this.due_date = due_date;
	}

	public int getIs_completed() {
		return is_completed;
	}

	public void setIs_completed(int is_completed) {
		this.is_completed = is_completed;
	}

	public String getExpected_time() {
		return expected_time;
	}

	public void setExpected_time(String expected_time) {
		this.expected_time = expected_time;
	}

	public String getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(String difficulty) {
		this.difficulty = difficulty;
	}

	@Override
	public String toString() {
		String line = "";
		if(is_completed==0)
			line = id + " " + "[" + category + "] " + title + " - " + desc + " - " + due_date + " - " + expected_time + " - " + difficulty + " - " + current_date;
		else if(is_completed==1)
			line = id + " " + "[" + category + "] [V] " + title + " - " + desc + " - " + due_date + " - " + expected_time + " - " + difficulty + " - " + current_date;
		return line;
	}
	
	public String toSaveString() {
		return category + "##" + title + "##" + desc + "##" + due_date + "##" + current_date + "##" + is_completed+ "##" + expected_time + "##" + difficulty + "\n";
	}
    
}
