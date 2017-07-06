package uk.gov;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import uk.gov.resources.HelloWorld;

public class ExemplarApplication extends Application<ExemplarConfiguration> {

    public static void main(final String[] args) throws Exception {
        new ExemplarApplication().run(args);
    }

    @Override
    public String getName() {
        return "dropwizard-exemplar";
    }

    @Override
    public void initialize(final Bootstrap<ExemplarConfiguration> bootstrap) {
    }

    @Override
    public void run(final ExemplarConfiguration configuration,
                    final Environment environment) {
        environment.jersey().register(new HelloWorld());
    }

}
