package routes;

import com.google.common.collect.ImmutableMap;
import org.junit.Test;
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
public class IndexRouteTest extends WithApplication {

    @Override
    protected play.Application provideApplication() {
        return new FakeApplication(new java.io.File("."), Helpers.class.getClassLoader(),
                ImmutableMap.of("play.http.router", "router.Routes"), new ArrayList<String>(), null);
    }

    @Test
    public void rootRoute() {
        Result result = routeAndCall(fakeRequest(GET, "/"), 0);
        assertThat(result, is(notNullValue()));
    }

    @Test
    public void badRoute() {
        Result result = routeAndCall(fakeRequest(GET, "/bad"), 0);
        assertThat(result, is(nullValue()));
    }
}
