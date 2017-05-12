package config;

import db.DB;
import log.LOG;

public class Config {
    static public void init() throws Exception {
        Class.forName("org.sqlite.JDBC");
        String path = System.getProperty("user.home") + "/sflow-monitor";
        LOG.initLogger(path);
        DB.initDb(path);

    }

}
