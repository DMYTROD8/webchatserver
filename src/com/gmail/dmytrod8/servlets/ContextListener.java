package com.gmail.dmytrod8.servlets;

import com.gmail.dmytrod8.User;
import com.gmail.dmytrod8.dao.UserDAO;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.concurrent.atomic.AtomicReference;

import static com.gmail.dmytrod8.User.STATUS.*;

@WebListener
public class ContextListener implements ServletContextListener {
    /**
     * Fake database connector.
     */
    private AtomicReference<UserDAO> dao;

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {

        dao = new AtomicReference<>(new UserDAO());

        dao.get().add(new User(0, "admin", "1", Available));
        dao.get().add(new User(1, "user", "2", Busy));
        dao.get().add(new User(2, "test", "3", Away));

        final ServletContext servletContext =
                servletContextEvent.getServletContext();

        servletContext.setAttribute("dao", dao);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        dao = null;
    }
}
