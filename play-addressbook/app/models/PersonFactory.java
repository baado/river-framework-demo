package models;

import org.riverframework.core.Document;

/**
 * Created by mario.sotil on 10/27/2015.
 */
public interface PersonFactory {
    PersonInterface create(Document doc);
}
