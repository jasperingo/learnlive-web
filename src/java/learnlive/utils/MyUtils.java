
package learnlive.utils;

import java.security.SecureRandom;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;



public class MyUtils {
    
    
    public static String generateToken(int len, String chars) {
        SecureRandom rand = new SecureRandom();
        StringBuilder builder = new StringBuilder(len);
        for (int i=0; i<len; i++) 
            builder.append(chars.charAt(rand.nextInt(chars.length())));
        return builder.toString();
    }
    
    public static LocalDateTime convertDateToLocalDateTime(Date dateToConvert) {
        return dateToConvert.toInstant()
          .atZone(ZoneId.systemDefault())
          .toLocalDateTime();
    }
    
    public static LocalDateTime convertTimestampToLocalDateTime(Object dateToConvert) {
        return convertTimestampToLocalDateTime((Timestamp)dateToConvert);
    }
    
    public static LocalDateTime convertTimestampToLocalDateTime(Timestamp dateToConvert) {
        if (dateToConvert == null) return null;
        return dateToConvert.toLocalDateTime();
    }
    
    public static String formatDateTime(LocalDateTime ldt) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mma dd MMM yyyy");
        return ldt.format(formatter);
    }
    
    public static String[] getClassTimeStatus(LocalDateTime start, LocalDateTime end) {
        
        Timestamp startTime = Timestamp.valueOf(start);
        long startt = startTime.getTime();
        Date dd = new Date();
        long noww = dd.getTime();
        
        if (end != null) {
            return new String[] {
                "bg-secondary",
                "Ended"
            };
        }
        
        if (end == null && startt > noww) {
            return new String[] {
                "bg-orange",
                "Upcoming"
            };
        }
        
        return new String[] {
            "bg-success",
            "Live"
        };
    }
    
    
    
}


