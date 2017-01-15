package com.ayo.spider;

import java.util.ArrayList;
import java.util.List;

/**
 * 一个页面，包含下一级页面（以及下一级页面url的构造方式）
 * 
 * jsoup选择器：http://www.open-open.com/jsoup/selector-syntax.htm
 * 
 * @author Administrator
 *
 */
public abstract class Page {
	
	public String url; ///url--需要给值
	
	public Page(String url){
		this.url = url;
	}
	
	/**
	 * 网络请求，线程调度也放在这里，返回html内容
	 * @return
	 */
	protected abstract String craw();
	
	/**
	 * 分析网页，大部分情况会借助jsoup，生成业务bean，存到任何地方。特别注意，此时还应该生成subPages
	 * @param content
	 */
	protected abstract void anylise(String content);
	
	public void run(){
		try{
			String content = craw();
			if(content == null || content.equals("")){
				raiseCannotContinue("网页的内容是空--" + url); 
			}
			anylise(content);
		}catch(CannotContinueException e){
			e.printStackTrace();
		}
		
		if(subPages != null){
			for(Page page: subPages){
				if(page != null) page.run();
			}
		}
	}
	
	private List<Page> subPages = new ArrayList<>();
	
	public void addSubPage(Page p){
		if(subPages.contains(p)) return;
		subPages.add(p);
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Page){
			Page o = (Page) obj;
			if(o.url != null && (o.url.equals(this.url))){
				return true;
			}
		}
		return false;
	}
	
	public static class CannotContinueException extends RuntimeException{
		public CannotContinueException(Exception e){
			super(e);
		}
		public CannotContinueException(){
			super();
		}
		public CannotContinueException(String e){
			super(e);
		}
	}
	
	public static void raiseCannotContinue(Exception e){
		throw new CannotContinueException(e);
	}
	public static void raiseCannotContinue(){
		throw new CannotContinueException();
	}
	public static void raiseCannotContinue(String e){
		throw new CannotContinueException(e);
	}
}
