package edu.upenn.cis.cis455.webserver.context;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;

/**
 * @author brishi
 */
public class MyServletConfig implements ServletConfig {

    private final ServletContext context;
    private final String name;
    private final HashMap<String, String> initParameters;

    public MyServletConfig(String name, ServletContext context) {
        this.name = name;
        this.context = context;
        this.initParameters = new HashMap<>();
    }

    public MyServletConfig(String name, ServletContext context,
                           HashMap<String, String> initParameters) {

        this.name = name;
        this.context = context;
        this.initParameters = initParameters;
    }

    @Override
    public String getServletName() {
        return name;
    }

    @Override
    public ServletContext getServletContext() {
        return context;
    }

    @Override
    public String getInitParameter(String s) {
        return initParameters.get(s);
    }

    @Override
    public Enumeration getInitParameterNames() {
        return Collections.enumeration(initParameters.keySet());
    }

    public void setInitParam(String name, String value) {
        initParameters.put(name, value);
    }
}
