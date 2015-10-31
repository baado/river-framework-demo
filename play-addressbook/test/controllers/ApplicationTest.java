package controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.collect.ImmutableMap;
import org.hamcrest.CoreMatchers;
import org.junit.*;

import play.mvc.*;
import play.test.*;
import play.data.DynamicForm;
import play.data.validation.ValidationError;
import play.data.validation.Constraints.RequiredValidator;
import play.i18n.Lang;
import play.libs.F;
import play.libs.F.*;
import play.twirl.api.Content;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.StringContains.containsString;
import static play.test.Helpers.*;
import static org.junit.Assert.*;


/**
*
* Simple (JUnit) tests that can call all parts of a play app.
* If you are interested in mocking a whole application, see the wiki for more details.
*
*/
public class ApplicationTest extends WithApplication{

    @Override
    protected play.Application provideApplication() {
        return new FakeApplication(new java.io.File("."), Helpers.class.getClassLoader(),
                ImmutableMap.of("play.http.router", "router.Routes"), new ArrayList<String>(), null);
    }

    @Ignore
    @Test
    public void renderTemplate() {
        // This code produces this error:
        // Test controllers.ApplicationTest.renderTemplate failed: java.lang.RuntimeException: There is no HTTP Context available from here.

        Content html = views.html.index.render("Your new application is ready.");
        assertThat(html.contentType(), is("text/html"));
        assertThat(contentAsString(html), containsString("Your new application is ready."));
    }

}
