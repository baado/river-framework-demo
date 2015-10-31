package controllers;

import com.google.common.collect.ImmutableMap;
import data.Connection;
import data.Database;
import org.junit.Before;
import org.junit.Test;
import play.Play;
import play.mvc.Result;
import play.test.FakeApplication;
import play.test.Helpers;
import play.test.WithApplication;

import java.util.ArrayList;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static play.test.Helpers.*;
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
    public void list() {
        Result result = route(routes.Persons.list());
        assertThat(result.status(), is(OK));
    }

    @Test
    public void newPerson() {
        Result result = route(routes.Persons.newPerson());
        assertThat(result.status(), is(OK));
    }

    @Test
    public void details() {
        Result result = route(routes.Persons.details("DOESNOTEXIST"));
        assertThat(result.status(), is(SEE_OTHER));
        assertThat(result.redirectLocation(), is(routes.Persons.list().url()));

        result = route(routes.Persons.details("P1004"));
        assertThat(result.status(), is(OK));
    }

    @Test
    public void save() {
        Result result = routeAndCall(fakeRequest(POST, "/persons/P1004")
                .bodyFormArrayValues(ImmutableMap.<String, String[]>builder()
                        .put("firstName", new String[]{"John"})
                        .put("lastName", new String[]{"Doe"})
                        .put("email", new String[]{"john.doe@riverframework.org"})
                        .build()), 0);
        assertThat(result.status(), is(SEE_OTHER));
        assertThat(result.redirectLocation(), is(routes.Persons.list().url()));
    }

    @Test
    public void delete() {
        Result result = route(routes.Persons.delete("DOESNOTEXIST"));
        assertThat(result.status(), is(SEE_OTHER));
        assertThat(result.redirectLocation(), is(routes.Persons.list().url()));

        result = route(routes.Persons.delete("P1004"));
        assertThat(result.status(), is(SEE_OTHER));
        assertThat(result.redirectLocation(), is(routes.Persons.list().url()));
    }
}
