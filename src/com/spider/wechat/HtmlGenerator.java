package com.spider.wechat;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.imageio.ImageIO;


public class HtmlGenerator {

	 //java环境  
    public static void all(){  
        Properties props=System.getProperties();  
        System.out.println("Java的运行环境版本："+props.getProperty("java.version"));  
        System.out.println("Java的运行环境供应商："+props.getProperty("java.vendor"));  
        System.out.println("Java供应商的URL："+props.getProperty("java.vendor.url"));  
        System.out.println("Java的安装路径："+props.getProperty("java.home"));  
        System.out.println("Java的虚拟机规范版本："+props.getProperty("java.vm.specification.version"));  
        System.out.println("Java的虚拟机规范供应商："+props.getProperty("java.vm.specification.vendor"));  
        System.out.println("Java的虚拟机规范名称："+props.getProperty("java.vm.specification.name"));  
        System.out.println("Java的虚拟机实现版本："+props.getProperty("java.vm.version"));  
        System.out.println("Java的虚拟机实现供应商："+props.getProperty("java.vm.vendor"));  
        System.out.println("Java的虚拟机实现名称："+props.getProperty("java.vm.name"));  
        System.out.println("Java运行时环境规范版本："+props.getProperty("java.specification.version"));  
        System.out.println("Java运行时环境规范供应商："+props.getProperty("java.specification.vender"));  
        System.out.println("Java运行时环境规范名称："+props.getProperty("java.specification.name"));  
        System.out.println("Java的类格式版本号："+props.getProperty("java.class.version"));  
        System.out.println("Java的类路径："+props.getProperty("java.class.path"));  
        System.out.println("加载库时搜索的路径列表："+props.getProperty("java.library.path"));  
        System.out.println("默认的临时文件路径："+props.getProperty("java.io.tmpdir"));  
        System.out.println("一个或多个扩展目录的路径："+props.getProperty("java.ext.dirs"));  
        System.out.println("操作系统的名称："+props.getProperty("os.name"));  
        System.out.println("操作系统的构架："+props.getProperty("os.arch"));  
        System.out.println("操作系统的版本："+props.getProperty("os.version"));  
        System.out.println("文件分隔符："+props.getProperty("file.separator"));//在 unix 系统中是＂／＂ System.out.println("路径分隔符："+props.getProperty("path.separator"));//在 unix 系统中是＂:＂ System.out.println("行分隔符："+props.getProperty("line.separator"));//在 unix 系统中是＂/n＂ System.out.println("用户的账户名称："+props.getProperty("user.name"));  
        System.out.println("用户的主目录："+props.getProperty("user.home"));  
        System.out.println("用户的当前工作目录："+props.getProperty("user.dir"));  
    }  
    public static void main(String[] args) {  
        all();  
    }  
	
	public static void toHtml(List<WeChatTop> tops){
		
		Map data = new HashMap();
		data.put("tops", tops);
		
		
		FreeMaker.template("./", "top-list-tmpl.html", "./top-list.html", data);
		
		String path1111 = System.getProperty("user.dir");
		String url = ("file:///" + path1111 + "/top-list.html").replace("\\", "/");
		openBrowser(url);
		
	}
	
	
	
	public static void putContent(String absolutePath, String content){
		try {
			File f = new File(absolutePath);
			if(!f.exists()){
				f.createNewFile();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		FileOutputStream out = null;
		try {
			out = new FileOutputStream(new File(absolutePath));   
			out.write(content.getBytes()); 
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(out != null){
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		 
	}
	
	public static void openBrowser(String url){
		if(url == null){
			System.out.println("url为空");
			return;
		}
		if(java.awt.Desktop.isDesktopSupported()){
            try{
                //创建一个URI实例,注意不是URL
                java.net.URI uri=java.net.URI.create(url);
                //获取当前系统桌面扩展
                java.awt.Desktop dp=java.awt.Desktop.getDesktop();
                //判断系统桌面是否支持要执行的功能
                if(dp.isSupported(java.awt.Desktop.Action.BROWSE)){
                    //获取系统默认浏览器打开链接
                    dp.browse(uri);
                }else{
                	System.out.println("不支持打开浏览器功能");
                }
            }catch(java.io.IOException e){
                //此为无法获取系统默认浏览器
            	e.printStackTrace();
            	System.out.println("无法获取系统默认浏览器");
            }
        }
	}
	
	public static String getFileSize(String path){
		 File f= new File(path);  
		    if (f.exists() && f.isFile()){  
		          return getDataSize(f.length());
		    }else{  
		    	return getDataSize(0);
		    }  
	}

	public static String getDataSize(long size){  
        DecimalFormat formater = new DecimalFormat("####.00");  
        if(size<1024){  
            return size+"bytes";  
        }else if(size<1024*1024){  
            float kbsize = size/1024f;    
            return formater.format(kbsize)+"KB";  
        }else if(size<1024*1024*1024){  
            float mbsize = size/1024f/1024f;    
            return formater.format(mbsize)+"MB";  
        }else if(size<1024*1024*1024*1024){  
            float gbsize = size/1024f/1024f/1024f;    
            return formater.format(gbsize)+"GB";  
        }else{  
            return "size: error";  
        }  
	}
	
	public static int[] getImageSize(String path){
		File picture = new File(path);
        BufferedImage sourceImg;
		try {
			sourceImg = ImageIO.read(new FileInputStream(picture));
//			System.out.println(String.format("%.1f",picture.length()/1024.0));// 源图大小
//	        System.out.println(sourceImg.getWidth()); // 源图宽度
//	        System.out.println(sourceImg.getHeight()); // 源图高度
	        return new int[]{sourceImg.getWidth(), sourceImg.getHeight()};
		} catch (FileNotFoundException e) {
			System.out.println("出错：" + path);
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("出错：" + path);
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("出错：" + path);
			e.printStackTrace();
		} 
        return new int[]{0, 0};
	}
	
	public static boolean isImage(String url){
		
		if(url != null && (url.endsWith("jpg") || url.endsWith("png") || url.endsWith("gif") || url.equals("jpeg") )){
			return true;
		}
		return false;
	}
	
}
