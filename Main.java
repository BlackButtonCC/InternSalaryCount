package pachong;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    static String SendGet(String url){
    	String result = null;//定义一个字符串来存储网页内容
    	BufferedReader in = null ;//空
    	try{
    		URL realUrl = new URL(url);
    		URLConnection connection =realUrl.openConnection();//返回一个 URLConnection 对象，它表示到 URL 所引用的远程对象的连接。
    		connection.connect();// 打开到此 URL 引用的资源的通信链接（如果尚未建立这样的连接）。
    		in = new BufferedReader(new InputStreamReader(connection.getInputStream(),"gbk"));//字节流转化为字符流通过缓冲流读操作
    		String line;
    		while((line=in.readLine())!=null){
    			result +=line+"\r\n";
    			System.out.println(result);
    		}
    	}catch(Exception e){
    		e.printStackTrace();
    	}finally{
    		try{
    			if(in !=null){
    				in.close();
    			}
    		}catch(Exception e2){
    			e2.printStackTrace();
    		}
    	}
    	
    	return result;
    }
	public static Map<String, String> transStringToMap(String mapString){
		String [] str;
		Map<String, String> map = new HashMap<String, String>();
		String pattern = "\\d{4}-\\d{1,2}-\\d{1,2}[,]\\d{2}[:]\\d{2}[:]\\d{2}[,]\\d{2}[:]\\d{2}[:]\\d{2}[,][\\u4E00-\\u9FA5]+";
		Pattern r = Pattern.compile(pattern);//将给定的正则表达式编译到模式中。
		Matcher m = r.matcher(mapString);// 创建匹配给定输入与此模式的匹配器
		
		while(m.find()){// 尝试查找与该模式匹配的输入序列的下一个子序列
//		m.find();
		str=m.group().split(",");//m.group:返回由以前匹配操作所匹配的输入子序列.split:根据给定正则表达式的匹配拆分此字符串,返回值为数组
		map.put(str[0], str[3]);
		}
		return map;
	}
	
	public static void main(String[] args) {
	    String url = "http://hq.sinajs.cn/?list=market_stock_sh";
	    SendGet(url);
        Map<String,String> map= transStringToMap(SendGet(url)); 
        for (String key : map.keySet()) {   
            System.out.println("key= " + key + "  and  value= " + map.get(key));   
        }
	}
}
