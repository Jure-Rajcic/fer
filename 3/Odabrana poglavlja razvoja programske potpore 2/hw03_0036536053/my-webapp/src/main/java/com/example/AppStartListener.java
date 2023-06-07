package com.example;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class AppStartListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        long startTime = System.currentTimeMillis();
        sce.getServletContext().setAttribute("startTime", startTime);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        // Do any cleanup if necessary
    }
}
