package com.GWTApp.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

import java.util.ArrayList;

public interface GWTAppServiceAsync {
    void getMessage(String msg, AsyncCallback<String> async);
    void getPersons(AsyncCallback<ArrayList<Person>> persons);
    void addPerson(Person p, AsyncCallback<Person> addCallback);
}
