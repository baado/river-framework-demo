package data;

import com.google.common.collect.ImmutableMap;
import org.junit.Test;
import play.Play;
import play.test.*;


import java.util.ArrayList;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;
import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.running;

/**
 * Created by mario.sotil on 10/28/2015.
 */
public class ConnectionTest extends WithApplication {

    @Override
    protected play.Application provideApplication() {
        return new FakeApplication(new java.io.File("."), Helpers.class.getClassLoader(),
                ImmutableMap.of("play.http.router", "router.Routes"), new ArrayList<String>(), null);
    }

    @Test
    public void inject() {
        Connection conn = Play.application().injector().instanceOf(Connection.class);
        assertThat(conn, is(notNullValue()));
    }
}
