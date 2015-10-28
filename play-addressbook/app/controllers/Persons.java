package controllers;

import lotus.domino.NotesThread;
import models.Person;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.persons.*;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by mario.sotil on 10/15/2015.
 */
public class Persons extends Controller {

    @Inject
    private Person _person;

    private final Form<Person> personForm = Form.form(Person.class);

    public Result list() {
        NotesThread.sinitThread();

        List<Person> persons = _person.listAll();

        NotesThread.stermThread();

        return ok(list.render(persons));
    }

    public Result newPerson() {
        return ok(details.render(personForm));
    }

    public Result details(String id) {
        NotesThread.sinitThread();

        final Person person = _person.findById(id);
        if (person == null) {
            flash("error", String.format("Person with id %s does not exist.", id));
            return redirect(routes.Persons.list());
        }
        Form<Person> filledForm = personForm.fill(person);

        NotesThread.stermThread();

        return ok(details.render(filledForm));

    }

    public Result save() {
        NotesThread.sinitThread();

        Form<Person> boundForm = personForm.bindFromRequest();
        if (boundForm.hasErrors()) {
            flash("error", "Please correct the form below.");
            return badRequest(details.render(boundForm));
        }

        Person person = boundForm.get();
        person.save();

        NotesThread.stermThread();

        flash("success", String.format("Successfully added person %s", person));
        return redirect(routes.Persons.list());
    }

    public Result delete(String id) {
        NotesThread.sinitThread();

        final Person person = _person.findById(id);
        if(person == null) {
            flash("error", String.format("Person with id %s does not exist.", id));
            return redirect(routes.Persons.list());
        }
        person.remove();

        NotesThread.stermThread();

        flash("success", String.format("Successfully removed person %s", person));
        return redirect(routes.Persons.list());
    }
}
