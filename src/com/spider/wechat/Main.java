package com.spider.wechat;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import org.ayo.lang.JsonUtilsUseFast;
import org.ayo.lang.Lang;

public class Main {

	/**

微信公众号的抓取:
- 主页是：http://weixin.sogou.com/weixin?type=2&query=%E9%9B%A8%E5%AE%AB%E7%90%B4%E9%9F%B3&ie=utf8&_sug_=y&_sug_type_=&w=01019900&sut=5109&sst0=1484409344685&lkt=1%2C1484409344577%2C1484409344577
	- type=1：公众号  type=2：文章
	- query是公众号或者文章关键字

	 */
	
	private static final String rootUrl = "http://weixin.sogou.com/weixin"
			+ "?type={type}"
			+ "&query={query}"
			+ "&ie=utf8"
			+ "&_sug_=y"
			+ "&_sug_type_="
			+ "&w=01019900"
			+ "&sut=5109"
			+ "&page={page}"
			+ "&sst0=1484409344685&lkt=1%2C1484409344577%2C1484409344577";
	private static final int TYPE_ARTICAL = 2;
	private static final int TYPE_ACCOUT = 1;
	
	
	//对于一个关键字，第一次抓取10页，
	//以后是一次一抓，每次抓取，只抓取3页---3是个经验数，防止有遗漏，抓取的文章首先得去重，如果以前抓过，则忽略（作者和题目一样，则视为同一篇）
	public static final int PAGE_COUNT_FIRST_TIME = 10;
	public static final int PAGE_COUNT_PER = 3; 
	
	public static void main(String[] args) {
		String[] keywords =  {
				"美腿",
				"美臀",
				"美胸",
				"妹子",
				"越南妹",
				"童颜",
				"巨乳",
				"二次元",
				"美女", 
				"美乳", 
				"嫩模", 
				"gif", 
				"网红",
				"性爱",
				"日本动作片",  
				"魔兽世界", 
				"守望先锋", 
				"屁股", 
				"海贼王", 
				"火影", 
				"生活大爆炸", 
				"av", 
				"女优", 
				"老司机", 
				"搞笑",
				"寒冬",
				"互联网",
				"安卓",
				"IT"};
		
		WeChatTopDao dao = WeChatTopDao.get();
		
		for(String keyword: keywords){
			
			///先看是不是第一次抓这个关键词
			System.out.println("\n\n\n抓关键词--" + keyword);
			try {
				keyword = URLEncoder.encode(keyword, "utf-8");
			} catch (UnsupportedEncodingException e1) {
				e1.printStackTrace();
			}
			int pageCount = Main.PAGE_COUNT_PER;
			if(dao.isFirstCrawWithKeyword(keyword)){
				pageCount = Main.PAGE_COUNT_FIRST_TIME;
			}
			
			///按页抓取
			for(int i = 1; i <= pageCount; i++){
				System.out.println("页数--" + i);
				String url = rootUrl.replace("{query}", keyword)
						.replace("{type}", TYPE_ARTICAL + "")
						.replace("{page}", i + "");
				System.out.println("url--" + url);
				WeChatRootPage page = new WeChatRootPage(url);
				page.run();
				
				///注意，如果run改成了异步运行，下面代码应该放在回调里
				List<WeChatTop> tops = page.getCrawedTops();
				if(Lang.isNotEmpty(tops)){
					///数据后处理：添加tag
					for(WeChatTop top: tops){
						top.tags += top.tags + "," + keyword;
						dao.insert(top);
					}
				}
				
				////结束一页，得休息30秒
				for(int k = 0; k < 30; k++){
					System.out.println("休息n秒....".replace("n", (30-k)+""));
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
			
			
		}
		
		///所有关键词所有页都抓完了
		List<WeChatTop> list = dao.getTopList();
		System.out.println("所有文章：\n" + JsonUtilsUseFast.toJson(list));
	}
	
}
