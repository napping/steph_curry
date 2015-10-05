package edu.upenn.cis.cis455.webserver.context;

import javax.servlet.*;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Set;

/**
 * @author brishi
 */
public class MyServletContext implements ServletContext {
	private HashMap<String,Object> attributes;
	private HashMap<String,String> initParameters;

    public MyServletContext() {
        attributes = new HashMap<>();
        initParameters = new HashMap<>();
    }

    @Override
    public ServletContext getContext(String s) {
        return this;
    }

    @Override
    public int getMajorVersion() {
        return 2;
    }

    @Override
    public int getMinorVersion() {
        return 4;
    }

    @Override
    public String getMimeType(String s) {
        // Does not need to be implemented.
        return null;
    }

    @Override
    public Set getResourcePaths(String s) {
        // Does not need to be implemented.
        return null;
    }

    @Override
    public URL getResource(String s) throws MalformedURLException {
        // Does not need to be implemented.
        return null;
    }

    @Override
    public InputStream getResourceAsStream(String s) {
        // Does not need to be implemented.
        return null;
    }

    @Override
    public RequestDispatcher getRequestDispatcher(String s) {
        // Does not need to be implemented.
        return null;
    }

    @Override
    public RequestDispatcher getNamedDispatcher(String s) {
        // Does not need to be implemented.
        return null;
    }

    @Deprecated
    @Override
    public Servlet getServlet(String s) throws ServletException {
        return null;
    }

    @Override
    public Enumeration getServlets() {
        // TODO Implement?
        return null;
    }

    @Deprecated
    @Override
    public Enumeration getServletNames() {
        return null;
    }

    @Override
    public void log(String s) {
        // Does not need to be implemented.
    }

    @Override
    public void log(Exception e, String s) {
        // Does not need to be implemented.
    }

    @Override
    public void log(String s, Throwable throwable) {
        // Does not need to be implemented.
    }

    @Override
    public String getRealPath(String s) {
        // TODO Implement?
        return null;
    }

    @Override
    public String getServerInfo() {
        // TODO Implement?
        return "Brian's CIS 555 Server.";
    }

    @Override
    public String getInitParameter(String s) {
        return initParameters.get(s);
    }

    @Override
    public Enumeration getInitParameterNames() {
        return Collections.enumeration(initParameters.keySet());
    }

    @Override
    public Object getAttribute(String s) {
        return attributes.get(s);
    }

    @Override
    public Enumeration getAttributeNames() {
        return Collections.enumeration(attributes.keySet());
    }

    @Override
    public void setAttribute(String s, Object o) {
        attributes.put(s, o);
    }

    @Override
    public void removeAttribute(String s) {
        attributes.remove(s);
    }

    @Override
    public String getServletContextName() {
        // TODO Implement?
        return "Brian's CIS 555 Servlet Context Name.";
    }
}
