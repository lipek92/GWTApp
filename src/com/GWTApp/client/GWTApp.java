package com.GWTApp.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.*;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;

import java.awt.*;
import java.util.ArrayList;

/**
 * Entry point classes define <code>onModuleLoad()</code>
 */
public class GWTApp implements EntryPoint {

    final FlexTable table = new FlexTable();

    final Label nameLabel = new Label("Imie");
    final Label surnameLabel = new Label("Nazwisko");
    final Label emailLabel = new Label("Email");
    final Label phoneLabel = new Label("Telefon");

    final TextBox name = new TextBox();
    final TextBox surname = new TextBox();
    final TextBox email = new TextBox();
    final TextBox phone = new TextBox();

    final Button add = new Button("Dodaj");
    final Button agree = new Button("Modyfikuj");
    final Button clean = new Button("Wyczyść");
    HandlerRegistration handler;

    public void onModuleLoad() {

        GWTAppService.App.getInstance().getPersons(new getPersonsCallback());

        add.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                Person p = new Person(name.getValue(), surname.getValue(), email.getValue(), Integer.parseInt(phone.getValue()));
                GWTAppService.App.getInstance().addPerson(p, new addCallback());
            }
        });

        clean.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                cleanTextFields();
            }
        });

        handler = agree.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {

            }
        });

        agree.setEnabled(false);


        // Assume that the host HTML has elements defined whose
        // IDs are "slot1", "slot2".  In a real app, you probably would not want
        // to hard-code IDs.  Instead, you could, for example, search for all
        // elements with a particular CSS class and replace them with widgets.
        //
        RootPanel.get("slot1").add(nameLabel);
        RootPanel.get("slot1").add(name);
        RootPanel.get("slot1").add(surnameLabel);
        RootPanel.get("slot1").add(surname);
        RootPanel.get("slot1").add(emailLabel);
        RootPanel.get("slot1").add(email);
        RootPanel.get("slot1").add(phoneLabel);
        RootPanel.get("slot1").add(phone);
        RootPanel.get("slot1").add(add);
        RootPanel.get("slot1").add(agree);
        RootPanel.get("slot1").add(clean);

        RootPanel.get("slot2").add(table);
    }

    private class getPersonsCallback implements AsyncCallback<ArrayList<Person>> {

        @Override
        public void onSuccess(ArrayList<Person> result) {
            addPersonsToView(result);
        }
        @Override
        public void onFailure(Throwable caught) {
            Window.alert("Błąd podczas pobierania danych z serwera!");
        }
    }

    private class addCallback implements AsyncCallback<ArrayList<Person>> {

        @Override
        public void onSuccess(ArrayList<Person> result) {
            addPersonsToView(result);
        }
        @Override
        public void onFailure(Throwable caught) {
            Window.alert("Błąd podczas dodawania!");
        }
    }

    private class deleteCallback implements AsyncCallback<ArrayList<Person>> {

        @Override
        public void onSuccess(ArrayList<Person> result) {
            addPersonsToView(result);
            add.setEnabled(true);
            agree.setEnabled(false);
        }
        @Override
        public void onFailure(Throwable caught) {
            Window.alert("Błąd podczas usuwania!");
        }
    }

    private class editCallback implements AsyncCallback<ArrayList<Person>>
    {
        @Override
        public void onSuccess(ArrayList<Person> result) {
            addPersonsToView(result);
            add.setEnabled(true);
            agree.setEnabled(false);
            cleanTextFields();
        }

        @Override
        public void onFailure(Throwable caught) {
            Window.alert("Błąd podczas edycji!");
        }
    }

    private class findCallback implements AsyncCallback<Person>
    {
        @Override
        public void onSuccess(Person result) {
            agree.setEnabled(true);

            name.setText(result.getName());
            surname.setText(result.getSurname());
            email.setText(result.getEmail());
            phone.setText(String.valueOf(result.getPhone()));
        }

        @Override
        public void onFailure(Throwable caught) {
            Window.alert("Błąd podczas wyszukiwania!");
        }
    }

    public void addPersonsToView(ArrayList<Person> persons)
    {
        table.removeAllRows();

        table.setText(0, 0, "Imie");
        table.setText(0, 1, "Nazwisko");
        table.setText(0, 2, "Email");
        table.setText(0, 3, "Telefon");
        table.setText(0, 4, "Usun");
        table.setText(0, 5, "Edytuj");


        for(int i = 0; i<persons.size(); i++)
        {
            final int temp = i;
            Person p =  persons.get(i);

            Button delete = new Button("Usuń");
            Button edit = new Button("Edytuj");

            edit.addClickHandler(new ClickHandler() {
                @Override
                public void onClick(ClickEvent event) {

                    GWTAppService.App.getInstance().findPerson(temp, new findCallback());

                    add.setEnabled(false);

                    handler.removeHandler();
                    handler = agree.addClickHandler(new ClickHandler() {
                        @Override
                        public void onClick(ClickEvent event) {
                            GWTAppService.App.getInstance().editPerson(temp, name.getText(), surname.getText(), email.getText(), Integer.parseInt(phone.getText()), new editCallback());
                        }
                    });
                }
            });

            delete.addClickHandler(new ClickHandler() {
                @Override
                public void onClick(ClickEvent event) {
                    GWTAppService.App.getInstance().deletePerson(temp, new deleteCallback());
                }
            });

            table.setText(i+1, 0, p.getName());
            table.setText(i+1, 1, p.getSurname());
            table.setText(i+1, 2, p.getEmail());
            table.setText(i+1, 3, String.valueOf((p.getPhone())));
            table.setWidget(i+1, 4, delete);
            table.setWidget(i+1, 5, edit);
        }
    }

//    public void addPersonToView(Person p)
//    {
//        final int rowCount = table.getRowCount();
//        Button delete = new Button("Usuń");
//        Button edit = new Button("Edytuj");
//
//        delete.addClickHandler(new ClickHandler() {
//            @Override
//            public void onClick(ClickEvent event) {
//                GWTAppService.App.getInstance().deletePerson(rowCount, new deleteCallback());
//            }
//        });
//
//        edit.addClickHandler(new ClickHandler() {
//            @Override
//            public void onClick(ClickEvent event) {
//
//                GWTAppService.App.getInstance().findPerson(rowCount, new findCallback());
//
//                add.setEnabled(false);
//
//                agree.addClickHandler(new ClickHandler() {
//                    @Override
//                    public void onClick(ClickEvent event) {
//                        GWTAppService.App.getInstance().editPerson(rowCount, name.getText(), surname.getText(), email.getText(), Integer.parseInt(phone.getText()), new editCallback());
//                    }
//                });
//
//
//            }
//        });
//
//        table.setText(rowCount, 0, p.getName());
//        table.setText(rowCount, 1, p.getSurname());
//        table.setText(rowCount, 2, p.getEmail());
//        table.setText(rowCount, 3, String.valueOf((p.getPhone())));
//        table.setWidget(rowCount, 4, delete);
//        table.setWidget(rowCount, 5, edit);
//
//    }

    public void cleanTextFields()
    {
        name.setText("");
        surname.setText("");
        email.setText("");
        phone.setText("");
    }
}
