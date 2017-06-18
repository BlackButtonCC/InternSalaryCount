package indi.cc;

import java.text.*;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Pattern;

/**
 * 计算周末双休的工作日
 * Author : CharlesChen
 * Time : 2017-05-19 13:28
 * Version : 1.0 仅能计算周末双休的工作日，对于单双交替的工作日不适用，还是先搞明白怎么如何得知单双交替周的规则再说吧:)
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
     * 利用JDK的Date类等计算出时间，再用正则表达式进行匹配进行周末和工作日的识别。
     * @return
     */
    public long countWorkday() {
        if (isRightDate(this.startDate) && isRightDate(this.endDate)) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            try {
                Date startDate = format.parse(this.startDate);
                Date endDate = format.parse(this.endDate);
                long startTime = startDate.getTime();
                long endTime = endDate.getTime();
                long allDay = (endTime - startTime) / (1000 * 60 * 60 * 24) + 1;
                long weekendDay = countWeekendDay(allDay);

                return allDay - weekendDay;
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("程序错误，请参考上诉原因...");
        }

        return 0;
    }

    /**
     * 算出周末的天数
     * @param addDay 总共的天数
     * @return long 周末的天数
     */
    public long countWeekendDay(long addDay) {
        long count = 0;
        long loop = addDay;
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        for (int i = 0; i < loop; i++) {
            try {
                Date date = new Date(format.parse(this.startDate).getTime() + 1000*3600*24*i);
                Boolean isMatch = Pattern.matches("^Sun.*|^Sat.*", date.toString());
                if (isMatch) {
                    ++count;
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
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
