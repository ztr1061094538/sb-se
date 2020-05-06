package com.tg.enterprise.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class UploadLog 
{
	private int id;
	
	private String action;
	
	private boolean status;
	
	private int retryTime;
	
	private boolean success;
	
	private String errorLog;
	
	private Date uploadTime;
}
