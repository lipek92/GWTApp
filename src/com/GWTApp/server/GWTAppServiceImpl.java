package com.GWTApp.server;

import com.GWTApp.client.Person;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.GWTApp.client.GWTAppService;

import java.util.ArrayList;

public class GWTAppServiceImpl extends RemoteServiceServlet implements GWTAppService {
    // Implementation of sample interface method
    public String getMessage(String msg) {
        return "Client said: \"" + msg + "\"<br>Server answered: \"Hi!\"";
    }

    private ArrayList<Person> persons = new ArrayList<Person>();

    public ArrayList<Person> getPersons() {
        return persons;
    }

    public Person addPerson(Person p)
    {
        persons.add(p);
        return p;
    }

    public ArrayList<Person> deletePerson(int id)
    {
        persons.remove(id);
        return persons;
    }

}