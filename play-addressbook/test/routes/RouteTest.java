package routes;

import com.google.common.collect.ImmutableMap;
import controllers.routes;
import org.junit.Test;
import play.mvc.Http;
import play.mvc.Result;
import play.test.FakeApplication;
import play.test.Helpers;
import play.test.WithApplication;

import java.util.ArrayList;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertThat;
import static play.test.Helpers.*;
import static play.test.Helpers.fakeApplication;

/**
 * Created by mario.sotil on 10/29/2015.
 */
public class RouteTest extends WithApplication {

    @Override
    protected play.Application provideApplication() {
        return new FakeApplication(new java.io.File("."), Helpers.class.getClassLoader(),
                ImmutableMap.of("play.http.router", "router.Routes"), new ArrayList<String>(), null);
    }

    @Test
    public void rootRoute() {
        Http.RequestBuilder request = new Http.RequestBuilder()
                .method(GET)
                .uri("/");
        Result result = route(request);
        assertThat(result.status(), is(SEE_OTHER));
        assertThat(result.redirectLocation(), is(routes.Persons.list().url()));    }

    @Test
    public void personsRoute() {
        Http.RequestBuilder request = new Http.RequestBuilder()
                .method(GET)
                .uri("/persons/");
        Result result = route(request);
        assertThat(result.status(), is(OK));
    }

    @Test
    public void badRoute() {
        Http.RequestBuilder request = new Http.RequestBuilder()
                .method(GET)
                .uri("/bad");
        Result result = route(request);
        assertThat(result.status(), is(NOT_FOUND));
    }
}
