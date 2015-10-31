package data;

import com.google.common.collect.ImmutableMap;
import org.junit.Test;
import org.riverframework.core.IndexedDatabase;
import play.Play;
import play.test.FakeApplication;
import play.test.Helpers;
import play.test.WithApplication;

import java.util.ArrayList;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.running;

/**
 * Created by mario.sotil on 10/28/2015.
 */
public class PersonTest extends WithApplication {

    @Override
    protected play.Application provideApplication() {
        return new FakeApplication(new java.io.File("."), Helpers.class.getClassLoader(),
                ImmutableMap.of("play.http.router", "router.Routes"), new ArrayList<String>(), null);
    }

    @Test
    public void crud() {
        Connection conn = Play.application().injector().instanceOf(Connection.class);
        Database database = conn.getDatabase();

        // CREATE
        Person person = database.createDocument(Person.class)
                .setField("FirstName", "John")
                .setField("LastName", "Doe")
                .setField("FullName", "John Doe")
                .setField("InternetAddress", "john.doe@riverframework.org")
                .generateId()
                .save();

        assertThat(person, is(notNullValue()));
        assertThat(person.isOpen(), is(true));

        assertThat(person.getFieldAsString("FirstName"), is("John"));
        assertThat(person.getFieldAsString("LastName"), is("Doe"));
        assertThat(person.getFieldAsString("FullName"), is("John Doe"));
        assertThat(person.getFieldAsString("InternetAddress"), is("john.doe@riverframework.org"));

        String id = person.getId();

        assertThat(id, is(notNullValue()));
        assertThat(id, is(not("")));

        // RETRIEVE
        person = null;
        person = database.getDocument(Person.class, id);

        assertThat(person, is(notNullValue()));
        assertThat(person.isOpen(), is(true));

        // UPDATE
        person.setField("LastName", "Lennon")
                .save();

        assertThat(person.getFieldAsString("LastName"), is("Lennon"));

        //DELETE
        person.delete();

        person = null;
        person = database.getDocument(Person.class, id);

        assertThat(person, is(notNullValue()));
        assertThat(person.isOpen(), is(false));
    }
}
