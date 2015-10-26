package models;

import data.Connection;
import org.riverframework.core.Database;
import org.riverframework.core.Document;
import play.Logger;
import play.data.validation.Constraints;

import java.util.ArrayList;
import java.util.List;

public class Person {

    public String id;
    @Constraints.Required
    public String firstName;
    @Constraints.Required
    public String lastName;
    @Constraints.Required
    public String email;

    private data.Person person;
    private Connection conn;

    public Person() {

    }

    public static List<Person> listAll(Connection conn) {
        List<Person> persons = new ArrayList<>();

        Database db = conn.getDatabase();

        for (Document doc: db.getView(db.getClosedDocument(data.Person.class).getIndexName()).getAllDocuments()) {
            data.Person _person = doc.getAs(data.Person.class);
            persons.add(new models.Person(_person));
        }

        return persons;
    }

    public static Person findById(Connection conn, String id) {

        Database db = conn.getDatabase();
        Logger.info(String.format("id=%s", id));
        data.Person _person = db.getDocument(data.Person.class, id);
        Logger.info(String.format("opened=%s", _person.isOpen() ? "true" : "false"));
        return _person.isOpen() ? new Person(_person) : null;
    }

    public Person(data.Person _person) {
        person = _person;

        if (_person != null && _person.isOpen()) {
            id = _person.getId();
            firstName = _person.getFieldAsString("FirstName");
            lastName = _person.getFieldAsString("LastName");
            email = _person.getFieldAsString("InternetAddress");
        }
    }

    public Person setConnection(Connection conn) {
        this.conn = conn;
        return this;
    }

    public void save() {
        Database db = conn.getDatabase();
        if (id == null || id.equals("")) {
            // It's a new person document
            person = db.createDocument(data.Person.class)
                    .generateId();

        } else {
            // We're updating a document
            if (person == null || !person.isOpen()) {
                // ... but the person document still was not loaded
                person = db.getDocument(data.Person.class, id);
                if (!person.isOpen()) {
                    Logger.error(String.format("It could not be possible to open a document with the id %s", id));
                    return;
                }
            }
        }

        person.setField("FirstName", firstName)
                .setField("LastName", lastName)
                .setField("FullName", String.format("%s %s", firstName, lastName))
                .setField("InternetAddress", email)
                .save();

    }

    public String toString() {
        return String.format("%s %s (%s)", firstName, lastName, id);
    }
}
