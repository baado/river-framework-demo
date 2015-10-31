import org.junit.*;

import play.mvc.*;
import play.test.*;
import play.libs.F.*;

import static play.test.Helpers.*;
import static org.junit.Assert.*;

import static org.fluentlenium.core.filter.FilterConstructor.*;

public class IntegrationTest {

    /**
     * add your integration test here
     * in this example we just check if the welcome page is being shown
     */
    @Ignore
    @Test
    public void test() {
        // This code produces this error:
        // [error] - com.gargoylesoftware.htmlunit.javascript.StrictErrorReporter - runtimeError: message=[An invalid or illegal selector was specified (selector: ':checked' error: Invalid selector: *:checked).] sourceName=[http://localhost:3333/assets/jquery/jquery.min.js] line=[2] lineSource=[null] lineOffset=[0]
        // [error] - com.gargoylesoftware.htmlunit.javascript.StrictErrorReporter - runtimeError: message=[An invalid or illegal selector was specified (selector: ':enabled' error: Invalid selector: *:enabled).] sourceName=[http://localhost:3333/assets/jquery/jquery.min.js] line=[2] lineSource=[null] lineOffset=[0]
        // [error] - com.gargoylesoftware.htmlunit.javascript.StrictErrorReporter - runtimeError: message=[The data necessary to complete this operation is not yet available.] sourceName=[http://localhost:3333/assets/jquery/jquery.min.js] line=[2] lineSource=[null] lineOffset=[0]
        // [error] - com.gargoylesoftware.htmlunit.javascript.StrictErrorReporter - runtimeError: message=[The data necessary to complete this operation is not yet available.] sourceName=[http://localhost:3333/assets/jquery/jquery.min.js] line=[2] lineSource=[null] lineOffset=[0]
        // [error] - com.gargoylesoftware.htmlunit.javascript.StrictErrorReporter - runtimeError: message=[The data necessary to complete this operation is not yet available.] sourceName=[http://localhost:3333/assets/jquery/jquery.min.js] line=[2] lineSource=[null] lineOffset=[0]
        // [error] - com.gargoylesoftware.htmlunit.javascript.StrictErrorReporter - runtimeError: message=[The data necessary to complete this operation is not yet available.] sourceName=[http://localhost:3333/assets/jquery/jquery.min.js] line=[2] lineSource=[null] lineOffset=[0]

        running(testServer(3333, fakeApplication(inMemoryDatabase())), HTMLUNIT, new Callback<TestBrowser>() {
            public void invoke(TestBrowser browser) {
                browser.goTo("http://localhost:3333");
                assertTrue(browser.pageSource().contains("Persons."));
            }
        });
    }

}
