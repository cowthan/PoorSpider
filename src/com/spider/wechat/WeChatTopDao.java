package com.spider.wechat;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;

import org.ayo.lang.Lang;
import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.dao.impl.NutDao;

import com.spider.wechat.model.WeChatTopDo;

public class WeChatTopDao {
	
	static org.apache.commons.dbcp.BasicDataSource dataSource; 
	static Dao dao;
	
	public static WeChatTopDao get(){
		if(dao == null){
			synchronized (Dao.class) {
				if(dao == null){
					dataSource = new org.apache.commons.dbcp.BasicDataSource();
					dataSource.setDriverClassName("com.mysql.jdbc.Driver");
					dataSource.setUrl("jdbc:mysql://localhost:3306/spider?characterEncoding=UTF-8");
					dataSource.setUsername("root");
					dataSource.setPassword("1234321");
					dataSource.setMaxActive(50);
					dataSource.setMaxIdle(30);
					dataSource.setMaxWait(1000);
					dataSource.setPoolPreparedStatements(true);
					dataSource.setMaxOpenPreparedStatements(-1);
					dao = new NutDao(dataSource);
				}
			}
		}
		return new WeChatTopDao();
	}

	
	public boolean isFirstCrawWithKeyword(String keyword){
		WeChatTopDo w = dao.fetch(WeChatTopDo.class, Cnd.where("tags", "like", "%" + keyword + "%"));
		return w == null; 
	}
	
	public int insert(WeChatTop top){
		
		if(alreadHas(top.title, top.createAt)){
			return -1;
		}
		WeChatTopDo w = WeChatTopDo.fromVO(top);
		dao.insert(w);
		return w.getId();
	}
	
	public boolean alreadHas(String title, String createAt){
		WeChatTopDo w = dao.fetch(WeChatTopDo.class, Cnd.where("title", "=", title).and("createAt", "=", createAt));
		return w != null;
	}
	
	public List<WeChatTop> getTopList(){
		List<WeChatTopDo> list = dao.query(WeChatTopDo.class, Cnd.where("id", ">", 0).desc("createAt"));
		if(Lang.isEmpty(list)){
			return null;
		}else{
			List<WeChatTop> res = new ArrayList<>();
			for(WeChatTopDo w: list){
				res.add(WeChatTopDo.toVO(w));
			}
			return res;
		}
	}
	
	/**
	 * 搜狐微信搜索搜出来的微信公众号详情链接，类似：
	 * http://mp.weixin.qq.com/s?src=3&timestamp=1484790800&ver=1&signature=mjeVYcvp8B*1tkRhsP7S3TAylcmmBvlaQKfAnGOu6MYTyDZC3IwtpUPwunVTdLK*sZfMTWin-V*tWPE2YueatzE*I9l8SHwZhbt0LzuXhGQkQSzNEQ17ms-Xtw8NSt-aGFzHtNkxUrXoyWQYTdbf8JJZdSTvbedgLtF*dGaG4tE=
	 * 其中的：
	 * timestamp=1484790800
	 * signature=mjeVYcvp8B*1tkRhsP7S3TAylcmmBvlaQKfAnGOu6MYTyDZC3IwtpUPwunVTdLK*sZfMTWin-V*tWPE2YueatzE*I9l8SHwZhbt0LzuXhGQkQSzNEQ17ms-Xtw8NSt-aGFzHtNkxUrXoyWQYTdbf8JJZdSTvbedgLtF*dGaG4tE=
	 * 这两个是每天都变的，用之前的值访问会提示访问链接失效
	 * 
	 * 此方法就是更新这两个参数
	 */
	public void refreshSignature(String timestamp, String signature){
		List<WeChatTopDo> list = dao.query(WeChatTopDo.class, Cnd.where("id", ">", 0).desc("createAt"));
		if(Lang.isEmpty(list)){
			return;
		}else{
			List<WeChatTop> res = new ArrayList<>();
			for(WeChatTopDo w: list){
				res.add(WeChatTopDo.toVO(w));
			}
		}
	}
}
