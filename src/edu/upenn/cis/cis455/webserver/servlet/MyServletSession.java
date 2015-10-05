package edu.upenn.cis.cis455.webserver.servlet;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionContext;
import java.rmi.server.UID;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;

/**
 * @author brishi
 */
public class MyServletSession implements HttpSession {
    private long creationTime;
    private String id;
    private long lastAccessedTime;
    private ServletContext context;

    private int maxInactiveInterval;

    private HashMap<String, Object> attributes;

    private boolean isValid;
    private boolean isNew;

    public MyServletSession(ServletContext context) {
        this.creationTime = new Date().getTime();
        this.id = new UID().toString();
        this.lastAccessedTime = this.creationTime;

        this.context = context;

        this.maxInactiveInterval = 30;
        this.lastAccessedTime = 0;

        this.attributes = new HashMap<>();

        this.isValid = true;
        this.isNew = true;
    }

    @Override
    public long getCreationTime() {
        return this.creationTime;
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public long getLastAccessedTime() {
        return this.lastAccessedTime;
    }

    @Override
    public ServletContext getServletContext() {
        return this.context;
    }

    @Override
    public void setMaxInactiveInterval(int i) {
        this.maxInactiveInterval = i;
    }

    @Override
    public int getMaxInactiveInterval() {
        return maxInactiveInterval;
    }

    @Deprecated
    @Override
    public HttpSessionContext getSessionContext() {
        return null;
    }

    @Override
    public Object getAttribute(String s) {
        return attributes.get(s);
    }

    @Override
    public Object getValue(String s) {
        return attributes.get(s);
    }

    @Override
    public Enumeration getAttributeNames() {
        return Collections.enumeration(attributes.keySet());
    }

    @Override
    public String[] getValueNames() {
        return (String[]) attributes.keySet().toArray();
    }

    @Override
    public void setAttribute(String s, Object o) {
        this.attributes.put(s, o);
    }

    @Override
    public void putValue(String s, Object o) {
        this.attributes.put(s, o);
    }

    @Override
    public void removeAttribute(String s) {
        this.attributes.remove(s);
    }

    @Override
    public void removeValue(String s) {
        this.attributes.remove(s);
    }

    @Override
    public void invalidate() {
        this.isValid = false;
    }

    @Override
    public boolean isNew() {
        return this.isNew;
    }
}
