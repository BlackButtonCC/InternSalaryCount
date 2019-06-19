package indi.cc;

import org.joda.time.Days;
import org.joda.time.LocalDate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Locale;

/**
 * 计算周末双休的工作日
 * Author : CharlesChen
 * Time : 2017-05-19 13:28
 * Version : 1.0 仅能计算周末双休的工作日。
 */
public class Count {
    private String startDate;
    private String endDate;

    public Count() {
    }

    public Count(String startDate, String endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    /**
     * V1.0：利用JDK的Date类等计算出时间，再用正则表达式进行匹配进行周末和工作日的识别。
     * V2.0：利用Joda-Time的LocalDate类
     * @return
     */
    public long countWorkday() {
        if (isRightDate(this.startDate) && isRightDate(this.endDate)) {
            LocalDate start = new LocalDate(this.startDate);
            LocalDate end = new LocalDate(this.endDate);

            long totalDays = Days.daysBetween(start, end).getDays() + 1;
            long weekendDay = countWeekendDay(totalDays, start);

            return totalDays - weekendDay;
        }

        return 0;
    }

    /**
     * 算出周末的天数
     * @param totalDays 总共的天数
     * @return long 周末的天数
     */
    public long countWeekendDay(long totalDays, LocalDate startDate) {
        long count = 0;
        long loop = totalDays;

        for (int i = 0; i < totalDays; i++) {
            if (startDate.toString("e").equals("6") || startDate.toString("e").equals("7")) {
                ++count;
            }
            startDate = startDate.plusDays(1);
        }
        
        return count;
    }

    /**
     * 日期格式检查，必须满足“year-mouth-day”格式。
     * @param data
     * @return
     */
    public boolean isRightDate(String data) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
            format.setLenient(false);
            format.parse(data);

            return true;
        } catch (Exception e) {
            System.out.println("错误的日期格式...");
            return false;
        }
    }

    /**
     * 利用爬虫对节假日进行爬取。
     * @return 返回当年节假日Map
     */
    public HashMap<String, String> getVacationDate() {
        String url = "http://hq.sinajs.cn/?list=market_stock_sh";
        HashMap<String, String> map = new HashMap<>(50);
        StringBuffer httpContent = httpRequst(url);

        try {
            byte[] bytes = httpContent.toString().getBytes("gbk");
            String s = new String(bytes, "gbk");
            System.out.println(s);

            /*Writer writer = new BufferedWriter(new FileWriter("aaa.txt"));
            writer.write(s.toString());
            writer.flush();
            writer.close();*/
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        /*try {
            String s = null;
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream("aaa.txt"));
            byte[] data1 = httpContent.toString().getBytes();
            bufferedOutputStream.write(data1, 0, data1.length);
            bufferedOutputStream.flush();
            bufferedOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        /*try {
            String s = new String(httpContent.toString().getBytes("utf-8"), "gbk");
            System.out.println(s);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }*/

        System.exit(0);

        return map;
    }

    /**
     * HTTP请求
     * @param url 字符串的URL
     * @return 返回网页内容
     */
    public StringBuffer httpRequst(String url) {
        String line;
        StringBuffer stringBuffer = new StringBuffer();
        try {
            URL handle = new URL(url);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(handle.openStream()));
            while ((line = bufferedReader.readLine()) != null) {
                //line = new String(line.getBytes("iso-8859-1"), "utf-8");
                stringBuffer.append(line);
            }
            bufferedReader.close();

            return stringBuffer;
        } catch (Exception e) { // Report any errors that arise
            stringBuffer.append(e.toString());
            System.err.println(e);
            System.err.println("Usage:   java   HttpClient   <URL>   [<filename>]");
        }

        return null;
    }
    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
