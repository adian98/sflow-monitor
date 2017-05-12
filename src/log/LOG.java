package log;

import java.io.File;
import java.util.logging.*;

public class LOG {
    static private Logger logger;

    static public void initLogger(String path) {
        LogFormatter.initFormatter();

        logger = Logger.getLogger("sflow-monitor");
        logger.setLevel(Level.ALL);
        //create folder
        File folder = new File(path);
        if (!folder.exists()) {
            folder.mkdir();
        }

        try {
            String log_path = path + "/sflow-monitor.log";
            FileHandler handler = new FileHandler(log_path);
            handler.setFormatter(new LogFormatter());
            logger.addHandler(handler);
        } catch (Exception e) {
            logger.warning("add file handler error");
        }
    }

    static public void INFO(String format, Object ... objects) {
        logger.log(Level.INFO, String.format(format, objects));
    }

    static public void ERROR(String format, Object ... objects) {
        logger.log(Level.SEVERE, String.format(format, objects));
    }

    static public void WARNING(String format, Object ... objects) {
        logger.log(Level.WARNING, String.format(format, objects));
    }

}






