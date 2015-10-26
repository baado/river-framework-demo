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

    private data.Person _person;
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
        data.Person _person = db.getDocument(data.Person.class, id);
        return _person.isOpen() ? new Person(_person) : null;
    }

    public Person(data.Person _person) {
        this._person = _person;

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
            // It's a new _person document
            _person = db.createDocument(data.Person.class)
                    .generateId();
            id = _person.getId();

        } else {
            // We're updating a document
            if (_person == null || !_person.isOpen()) {
                // ... but the _person document still was not loaded
                _person = db.getDocument(data.Person.class, id);
                if (!_person.isOpen()) {
                    Logger.error(String.format("It could not be possible to open a document with the id %s", id));
                    return;
                }
            }
        }

        _person.setField("FirstName", firstName)
                .setField("LastName", lastName)
                .setField("FullName", String.format("%s %s", firstName, lastName))
                .setField("InternetAddress", email)
                .save();

    }

    public void remove() {
        _person.delete();
        _person = null;
    }

    public String toString() {
        return String.format("%s %s (%s)", firstName, lastName, id);
    }
}
