package com.GWTApp.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import java.util.ArrayList;

@RemoteServiceRelativePath("GWTAppService")
public interface GWTAppService extends RemoteService {
    // Sample interface method of remote interface
    ArrayList<Person> getPersons();
    Person addPerson(Person p);
    ArrayList<Person> deletePerson(int id);
    Person findPerson(int id);
    ArrayList<Person> editPerson(int id, String name, String surname, String email, int phone);

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
