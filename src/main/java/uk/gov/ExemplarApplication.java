package uk.gov;

import io.dropwizard.Application;
import io.dropwizard.configuration.EnvironmentVariableSubstitutor;
import io.dropwizard.configuration.SubstitutingSourceProvider;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import uk.gov.resources.HelloWorld;

public class ExemplarApplication extends Application<ExemplarConfiguration> {
    public static void main(String[] args) throws Exception {
        if (args == null || args.length == 0) {
            new ExemplarApplication().run("server", System.getenv("CONFIG_FILE"));
        } else {
            new ExemplarApplication().run(args);
        }
    }

    @Override
    public String getName() {
        return "dropwizard-exemplar";
    }

    @Override
    public void initialize(final Bootstrap<ExemplarConfiguration> bootstrap) {
        bootstrap.setConfigurationSourceProvider(
                new SubstitutingSourceProvider(bootstrap.getConfigurationSourceProvider(),
                        new EnvironmentVariableSubstitutor(false)
                )
        );
    }

    @Override
    public void run(final ExemplarConfiguration configuration,
                    final Environment environment) {
        environment.jersey().register(new HelloWorld());
    }

}
