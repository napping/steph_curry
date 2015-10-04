package edu.upenn.cis.cis455.webserver.context;

import javax.servlet.*;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Enumeration;
import java.util.Set;

/**
 * @author brishi
 */
public class MyServletContext implements ServletContext {
    @Override
    public ServletContext getContext(String s) {
        return null;
    }

    @Override
    public int getMajorVersion() {
        return 0;
    }

    @Override
    public int getMinorVersion() {
        return 0;
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
        return null;
    }

    @Override
    public RequestDispatcher getNamedDispatcher(String s) {
        // Does not need to be implemented.
        return null;
    }

    @Override
    public Servlet getServlet(String s) throws ServletException {
        return null;
    }

    @Override
    public Enumeration getServlets() {
        return null;
    }

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
        return null;
    }

    @Override
    public String getServerInfo() {
        return null;
    }

    @Override
    public String getInitParameter(String s) {
        return null;
    }

    @Override
    public Enumeration getInitParameterNames() {
        return null;
    }

    @Override
    public Object getAttribute(String s) {
        return null;
    }

    @Override
    public Enumeration getAttributeNames() {
        return null;
    }

    @Override
    public void setAttribute(String s, Object o) {

    }

    @Override
    public void removeAttribute(String s) {

    }

    @Override
    public String getServletContextName() {
        return null;
    }
}
