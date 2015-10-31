package models;

import com.google.common.collect.ImmutableMap;
import data.Connection;
import data.Database;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import play.Play;
import play.test.FakeApplication;
import play.test.Helpers;
import play.test.WithApplication;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertThat;
import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.running;

/**
 * Created by mario.sotil on 10/31/2015.
 */
public class PersonTest extends WithApplication {

    @Override
    protected play.Application provideApplication() {
        return new FakeApplication(new java.io.File("."), Helpers.class.getClassLoader(),
                ImmutableMap.of("play.http.router", "router.Routes"), new ArrayList<String>(), null);
    }

    @Before
    public void reset() {
        Connection conn = Play.application().injector().instanceOf(Connection.class);

        Database database = conn.getDatabase();
        database.reset();
    }

    @Test
    public void listAll() {
        Connection conn = Play.application().injector().instanceOf(Connection.class);

        Database database = conn.getDatabase();
        Person _person = Play.application().injector().instanceOf(Person.class);
        List<Person> persons = _person.listAll();

        assertThat(persons, is(notNullValue()));
        assertThat(persons.size() > 0, is(true)); //greaterThan(0)
    }

    @Test
    public void findById() {
        Connection conn = Play.application().injector().instanceOf(Connection.class);

        Database database = conn.getDatabase();
        Person _person = Play.application().injector().instanceOf(Person.class);
        Person person = _person.findById("P1004");

        assertThat(person, is(notNullValue()));
        assertThat(person.id, is("P1004"));
    }

    @Test
    public void crud() {
        Connection conn = Play.application().injector().instanceOf(Connection.class);

        // CREATE
        Database database = conn.getDatabase();
        Person person = new Person();
        person.firstName = "John";
        person.lastName = "Doe";
        person.email = "john.doe@riverframework.org";
        person.save();

        String id = person.id;

        // RETRIEVE
        Person _person = Play.application().injector().instanceOf(Person.class);
        person = null;
        person = _person.findById(id);

        assertThat(person, is(notNullValue()));
        assertThat(person.lastName, is("Doe"));

        // UPDATE
        person.lastName = "Lennon";
        person.save();

        person = null;
        person = _person.findById(id);

        assertThat(person.lastName, is("Lennon"));

        // DELETE
        person.remove();
        person = null;
        person = _person.findById(id);

        assertThat(person, is(nullValue()));
    }
}
