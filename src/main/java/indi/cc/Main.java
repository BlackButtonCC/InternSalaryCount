package indi.cc;

/**
 * 运行文件。
 * Usage : java Main 2017-5-19 2017-5-25
 * Author : CharlesChen
 * Time : 2017-05-19 11:22
 * Version : 1.0
 */
public class Main {
    private static final int SALARY = 255;       //在这里写出您的每天实习工资
    //private static final int SCHOLARSHIP = 8 * 500;
    //private static final int FAMILY_SUPPORT = 8 * 1500;
    //private static final int TEACHER = 2800;


    public static void main(String args[]) {
        Count count = new Count(args[0], args[1]);

        //count.getVacationDate();

        long workDay = count.countWorkday();
        Double tax = ((SALARY * workDay - 800) >= 0) ? (SALARY * workDay - 800) * 0.2 : 0;        //实习按照的是劳务税，超过800扣除超过部分的20%。
        System.out.println("您在" + args[0] + "至" + args[1] + "期间，工作了" + workDay + "天");
        double total = (SALARY * workDay - tax);
        System.out.println("除去税收 " + tax + " 元，收入为 " + total + " 元");
    }
}
