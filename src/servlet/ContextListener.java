package servlet;

import config.Config;
import udpserver.UDPServer;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;


@WebListener
public class ContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        try {
            Config.init();
            Config.LOG_INFO("init");
            new Thread(new UDPServer(6343)).start();
        } catch (Exception e) {
            e.printStackTrace();
            Config.LOG_ERROR("init error : " + e.getMessage());
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        Config.LOG_INFO("shut down");
        Config.destroyed();
    }
}
