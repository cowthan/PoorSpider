package com.spider.wechat;

import java.util.ArrayList;
import java.util.List;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Name;
import org.nutz.dao.entity.annotation.Table;

@Table("top")
public class WeChatTop {
	
	@Id       // 表示该字段为一个自增长的Id,注意,是数据库表中自增!!
	public int id;
	
	@Column(value = "title")
	public String title;
	
	public List<String> coverImages = new ArrayList<>(); //可能空，可能1,2,3张
	
	public String shortIntro; //可能为空
	public int readCount = 0;
	public String createAt = "";
	public String topUrl = "";
	
	public String authorName = "";
	public String authorUrl = "";
	public String authorHeadImg = "";
	
	public String tags; ///这个字段是抓取之后，我们自己填上去的，如来自哪个关键词
}
