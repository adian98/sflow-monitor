package log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

class LogFormatter extends Formatter {
    static DateFormat dateFormat;
    static Calendar calendar;

    static public void initFormatter() {
        dateFormat = SimpleDateFormat.getDateTimeInstance(DateFormat.FULL, DateFormat.FULL);
        calendar = new GregorianCalendar();
    }

    @Override
    public String format(LogRecord logRecord) {
        calendar.setTimeInMillis(logRecord.getMillis());
        return String.format("[%s][%s] : %s\n",
                logRecord.getLevel(),
                dateFormat.format(calendar.getTime()),
                logRecord.getMessage());
    }
}