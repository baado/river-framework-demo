package controllers;

import lotus.domino.NotesThread;
import models.Person;
import org.riverframework.core.Database;
import org.riverframework.core.Document;
import play.mvc.Controller;
import play.mvc.Result;
import project.DatabaseConnection;
import views.html.persons.list;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mario.sotil on 10/15/2015.
 */
public class Persons extends Controller {

    @Inject
    DatabaseConnection conn;

    public Result list() {
        NotesThread.sinitThread();

        List<Person> persons = new ArrayList<>();

        Database db = conn.getDatabase();

        for (Document doc: db.getAllDocuments()) {
            Person person = doc.getAs(Person.class);
            persons.add(person);
        }

        NotesThread.stermThread();

        return ok(list.render(persons));
    }

    public Result newPerson() {
        return TODO;
    }

    public Result details(String id) {
        return TODO;
    }

    public Result save() {
        return TODO;
    }
}
