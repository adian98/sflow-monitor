package config;

import db.ConnectionPool;

import java.sql.Connection;
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
    private ConnectionPool connectionPool = new ConnectionPool(10);

    //singleton
    static Config config;

    static public void init() throws Exception {
        config = new Config();

        ConnectionPool.initDb();
    }

    private Config() {
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

    static public Connection getJdbcConnection() throws Exception {
        return config.connectionPool.get();
    }

    static public void putJdbcConnection(Connection conn) throws Exception{
        config.connectionPool.put(conn);
    }

    static public String dbPath() {
        return "jdbc:sqlite:/tmp/db.db";
    }
}
