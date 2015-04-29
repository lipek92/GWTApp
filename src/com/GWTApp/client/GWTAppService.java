package com.GWTApp.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("GWTAppService")
public interface GWTAppService extends RemoteService {
    // Sample interface method of remote interface
    String getMessage(String msg);

    /**
     * Utility/Convenience class.
     * Use GWTAppService.App.getInstance() to access static instance of GWTAppServiceAsync
     */
    public static class App {
        private static GWTAppServiceAsync ourInstance = GWT.create(GWTAppService.class);

        public static synchronized GWTAppServiceAsync getInstance() {
            return ourInstance;
        }
    }
}
