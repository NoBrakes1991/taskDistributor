package app.services;

import java.util.Calendar;
import java.util.Date;

public class EndDayReplace {

    public static Date getDate(String period) {
        Date endDate = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR,23);
        calendar.set(Calendar.MINUTE,59);
        calendar.set(Calendar.SECOND,59);
        calendar.set(Calendar.MILLISECOND,calendar.getActualMaximum(Calendar.MILLISECOND));
        switch (period) {
            case "lastQuarter": {
                calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) / 3 * 3 + 2);
                calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
                endDate = calendar.getTime();
            }
            break;
            case "lastMonth": {
                calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH));
                calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
                endDate = calendar.getTime();
            }
            break;
            case "lastWeek": {
                calendar.set(Calendar.DAY_OF_WEEK, 8);
                endDate = calendar.getTime();
            }
            break;
            case "currentQuarterToDate": {
                endDate = calendar.getTime();
            }
            break;
            case "currentMonthToDate": {
                endDate = calendar.getTime();
            }
            break;
            case "currentWeekToDate": {
                endDate = calendar.getTime();
            }
            break;
        }
        return endDate;
    }}
