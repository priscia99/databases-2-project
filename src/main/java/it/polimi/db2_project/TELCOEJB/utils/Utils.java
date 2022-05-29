package it.polimi.db2_project.TELCOEJB.utils;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Random;
public class Utils {
    /**
     * Attempts to pay an order with a random result
     * @return true if the payment attempt succeeded, false otherwise
     */
    public static Boolean pay(){
        Random rand = new Random();
        return rand.nextInt(2) == 1;

    }

    /**
     * Retrieves the local time
     * @return the timestamp with the local time
     */
    public static Timestamp getNowTime(){
        Date nowDate = new Date();
        LocalDateTime now = Instant.ofEpochMilli(nowDate.getTime()).atZone(ZoneId.systemDefault()).toLocalDateTime();
        Timestamp nowTime = Timestamp.valueOf(now);
        return nowTime;
    }
}
