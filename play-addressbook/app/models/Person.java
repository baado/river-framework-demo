package models;

import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;
import data.Connection;
import org.riverframework.core.Database;
import org.riverframework.core.Document;
import org.riverframework.core.DocumentIterator;
import play.Logger;
import play.Play;
import play.data.validation.Constraints;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class Person implements PersonInterface {

    private Connection conn;
    private data.Person _person;

    @Inject
    private PersonFactory personFactory;

    public String id;
    @Constraints.Required
    public String firstName;
    @Constraints.Required
    public String lastName;
    @Constraints.Required
    public String email;

    private void setConn() {
        conn = Play.application().injector().instanceOf(Connection.class);
    }

    public Person() {
        setConn();
    }

    @AssistedInject
    public Person(@Assisted Document doc) {
        setConn();

        _person = doc.getAs(data.Person.class);

        if (_person != null && _person.isOpen()) {
            id = _person.getId();
            firstName = _person.getFieldAsString("FirstName");
            lastName = _person.getFieldAsString("LastName");
            email = _person.getFieldAsString("InternetAddress");
        }
    }

    public List<Person> listAll() {
        Database db = conn.getDatabase();
        DocumentIterator it = db.getView(db.getClosedDocument(data.Person.class).getIndexName()).getAllDocuments();
        Iterable<Document> iterable = () -> it;
        Stream<Document> stream = StreamSupport.stream(iterable.spliterator(), false);

        List<Person> persons = stream.map(doc -> (Person) personFactory.create(doc)).collect(Collectors.toList());

        return persons;
    }

    public Person findById(String id) {

        Database db = conn.getDatabase();
        data.Person p = db.getDocument(data.Person.class, id);
        return p.isOpen() ? new Person(p) : null;
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
