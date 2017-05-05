package config;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.logging.*;


class LogFormatter extends Formatter {

    static DateFormat dateFormat = SimpleDateFormat.getDateTimeInstance(DateFormat.FULL, DateFormat.FULL);
    static Calendar calendar = new GregorianCalendar();

    @Override
    public String format(LogRecord logRecord) {
        calendar.setTimeInMillis(logRecord.getMillis());
        return String.format("[%s][%s] : %s\n",
                logRecord.getLevel(),
                dateFormat.format(calendar.getTime()),
                logRecord.getMessage());
    }
}


public class Config {
    private Logger logger = Logger.getLogger("sflow-monitor");


    //singleton
    static Config config = new Config();

    public Config() {
        //defaut
        logger.setLevel(Level.ALL);

        try {
            FileHandler handler = new FileHandler("/tmp/text.log");
            handler.setFormatter(new LogFormatter());
            logger.addHandler(handler);
        } catch (Exception e) {
            logger.warning("add file handler error");
        }
    }

    static public void LOG_INFO(String format, Object ... objects) {
        String s = String.format(format, objects);
        config.logger.log(Level.INFO, s);
    }

    static public void LOG_ERROR(String format, Object ... objects) {
        String s = String.format(format, objects);
        config.logger.log(Level.SEVERE, s);
    }
}
