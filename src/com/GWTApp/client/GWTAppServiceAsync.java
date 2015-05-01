package com.GWTApp.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

import java.util.ArrayList;

public interface GWTAppServiceAsync {
    void getPersons(AsyncCallback<ArrayList<Person>> persons);
    void addPerson(Person p, AsyncCallback<Person> addCallback);
    void deletePerson(int id, AsyncCallback<ArrayList<Person>> persons);
}
