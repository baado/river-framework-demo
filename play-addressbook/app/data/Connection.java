package data;

import lotus.notes.NotesThread;
import org.riverframework.River;
import org.riverframework.core.Session;
import play.Configuration;
import play.Logger;
import play.Play;
import play.inject.ApplicationLifecycle;
import play.libs.F;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by mario.sotil on 10/20/2015.
 */
@Singleton
public class Connection {

    private Session session;
    private Database database;

    @Inject
    public Connection(ApplicationLifecycle lifecycle) {
        Configuration conf = Play.application().configuration();

        String server = (String) null;
        String username = (String) null;
        String password = conf.getString("river.addressbook.password");
        String filepath = conf.getString("river.addressbook.filepath");

        // Creating a new Notes thread
        NotesThread.sinitThread();

        // This will close the session and the Notes thread
        lifecycle.addStopHook(() -> {
            if (session != null) session.close();
            NotesThread.stermThread();

            Logger.info("River session stopped");

            return F.Promise.pure(null);
        });

        // Opening a new River Framework session
        session = River.getSession(River.LOTUS_DOMINO, server, username, password);
        Logger.info("River session opened: " + session.getUserName());

        // Opening the database
        database = session.getDatabase(Database.class, "", filepath);

        // If not, then we create a new one
        if (!database.isOpen()) {
            database = session.createDatabase(Database.class, "", filepath);

            if (!database.isOpen()) {
                Logger.error("I could not create the test database at the local client, with the name '" + filepath + "'");
            } else {
                Logger.info("Database opened: " + database.getName());
                database.reset();
            }

        }
    }

    public Session getSession() {
        return session;
    }

    public Database getDatabase() {
        return database;
    }

}
