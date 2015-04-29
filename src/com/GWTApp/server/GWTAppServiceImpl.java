package com.GWTApp.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.GWTApp.client.GWTAppService;

public class GWTAppServiceImpl extends RemoteServiceServlet implements GWTAppService {
    // Implementation of sample interface method
    public String getMessage(String msg) {
        return "Client said: \"" + msg + "\"<br>Server answered: \"Hi!\"";
    }
}