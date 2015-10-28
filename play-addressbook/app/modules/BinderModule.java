package modules;

import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.assistedinject.FactoryModuleBuilder;
import models.Person;
import models.PersonFactory;
import models.PersonInterface;

/**
 * Created by mario.sotil on 10/27/2015.
 */
public class BinderModule implements Module {
    public void configure(Binder binder) {
        binder.install(new FactoryModuleBuilder()
                .implement(PersonInterface.class, Person.class)
                .build(PersonFactory.class));
    }
}
