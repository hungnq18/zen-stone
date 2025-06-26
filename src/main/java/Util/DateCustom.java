package Util;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author Le Tan Kim
 */
public class DateCustom {

    public Timestamp getCurrentTime() {
        LocalDateTime dateTime = LocalDateTime.now();
        Timestamp currentTime = Timestamp.valueOf(dateTime);
        return currentTime;
    }

    public Timestamp convertStringToTimestamp(String dateString) {
        try {
            String DATE_FORMAT = "yyyy-MM-dd";
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
            LocalDate localDate = LocalDate.parse(dateString, formatter);
            LocalDateTime localDateTime = localDate.atStartOfDay();
            Timestamp timestamp = Timestamp.valueOf(localDateTime);
            return timestamp;
        } catch (Exception e) {
            System.out.println("Error date convert: " + e);
            return null;
        }
    }

    public Date convertStringToDate(String dateString) {
        try {
            if (dateString != null) {
                Date localDate = Date.valueOf(dateString);
                return localDate;
            } else {
                System.out.println("Error: dateString is null or empty");
                return null;
            }
        } catch (Exception e) {
            System.out.println("Error date convert: " + e.getMessage());
            return null;
        }
    }

}
