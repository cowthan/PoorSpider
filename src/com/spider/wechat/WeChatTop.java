package com.spider.wechat;

import java.util.ArrayList;
import java.util.List;

public class WeChatTop {
	
	public String title;
	public List<String> coverImages = new ArrayList<>(); //可能空，可能1,2,3张
	public String desc; //可能为空
	public int readCount = 0;
	public String createAt = "";
	public String topUrl = "";
	
	public String authorName = "";
	public String authorUrl = "";
	public String authorHeadImg = "";
}
