package servlet;

import config.Config;
import db.DB;
import log.LOG;
import udpserver.UDPServer;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;


@WebListener
public class ContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        try {
            Config.init();
            LOG.INFO("init");
            new Thread(new UDPServer(6343)).start();
        } catch (Exception e) {
            e.printStackTrace();
            LOG.ERROR("init error : " + e.getMessage());
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        LOG.INFO("shut down");
        try {
            DB.db_conn.commit();
            DB.db_conn.close();
        } catch (Exception e) {
            //skip error
        }
    }
}
