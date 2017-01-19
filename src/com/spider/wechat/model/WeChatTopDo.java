package com.spider.wechat.model;

import java.util.ArrayList;
import java.util.List;

import org.ayo.lang.JsonUtilsUseFast;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Name;
import org.nutz.dao.entity.annotation.Table;

import com.spider.wechat.WeChatTop;

@Table("top")
public class WeChatTopDo {
	
	@Id       // 表示该字段为一个自增长的Id,注意,是数据库表中自增!!
	public int id;
	
	@Column(value = "title")
	public String title;
	
	@Column
	public String coverImages = "[]"; //可能空，可能1,2,3张
	
	@Column
	public String shortIntro; //可能为空
	
	@Column
	public int readCount = 0;
	
	@Column
	public String createAt = "";
	
	@Column
	public String topUrl = "";
	
	@Column
	public String authorName = "";
	
	@Column
	public String authorUrl = "";
	
	@Column
	public String authorHeadImg = "";

	@Column
	public String tags; ///这个字段是抓取之后，我们自己填上去的，如来自哪个关键词
	
	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
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

	public String getCoverImages() {
		return coverImages;
	}

	public void setCoverImages(String coverImages) {
		this.coverImages = coverImages;
	}

	public String getShortIntro() {
		return shortIntro;
	}

	public void setShortIntro(String shortIntro) {
		this.shortIntro = shortIntro;
	}

	public int getReadCount() {
		return readCount;
	}

	public void setReadCount(int readCount) {
		this.readCount = readCount;
	}

	public String getCreateAt() {
		return createAt;
	}

	public void setCreateAt(String createAt) {
		this.createAt = createAt;
	}

	public String getTopUrl() {
		return topUrl;
	}

	public void setTopUrl(String topUrl) {
		this.topUrl = topUrl;
	}

	public String getAuthorName() {
		return authorName;
	}

	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}

	public String getAuthorUrl() {
		return authorUrl;
	}

	public void setAuthorUrl(String authorUrl) {
		this.authorUrl = authorUrl;
	}

	public String getAuthorHeadImg() {
		return authorHeadImg;
	}

	public void setAuthorHeadImg(String authorHeadImg) {
		this.authorHeadImg = authorHeadImg;
	}
	
	
	public static WeChatTopDo fromVO(WeChatTop t){
		WeChatTopDo w = new WeChatTopDo();
		
		w.authorHeadImg = t.authorHeadImg;
		w.authorName = t.authorName;
		w.authorUrl = t.authorUrl;
		w.coverImages = JsonUtilsUseFast.toJson(t.coverImages);
		w.createAt = t.createAt;
		w.shortIntro = t.shortIntro;
		w.id = t.id;
		w.readCount = t.readCount;
		w.title = t.title;
		w.topUrl = t.topUrl;
		w.tags = t.tags;
		
		return w;
	}
	
	public static WeChatTop toVO(WeChatTopDo t){
		WeChatTop w = new WeChatTop();
		
		w.authorHeadImg = t.authorHeadImg;
		w.authorName = t.authorName;
		w.authorUrl = t.authorUrl;
		w.coverImages = JsonUtilsUseFast.getBeanList(t.coverImages, String.class);
		w.createAt = t.createAt;
		w.shortIntro = t.shortIntro;
		w.id = t.id;
		w.readCount = t.readCount;
		w.title = t.title;
		w.topUrl = t.topUrl;
		w.tags = t.tags;
		
		return w;
	}
}
