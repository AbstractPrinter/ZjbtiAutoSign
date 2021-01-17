package cn.wangyudi.zjbtiautosign;

import java.time.LocalTime;

/**
 * Hello world!
 * @author AbstractPrinter
 */
public class App {
    public static boolean isSigned = false;
    public static void main(String[] args) {
        System.out.println("智慧微学工自动签到");
        System.out.println("Start...");
        while (true) {
            LocalTime time = LocalTime.now();
            if (time.getHour() != 21) {
                isSigned = false;
            }
            if (!isSigned && time.getHour() == 21) {
                Sign sign = new Sign();
                sign.set("29.869333", "121.50293", "12502", "oTWTK0iicgxIg-r-wq6BF-Z2_x3I", "c8f57412c7c6ee4104710702cf3e2ccd")
                        .build();
            }
            try {
                Thread.sleep(60000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
