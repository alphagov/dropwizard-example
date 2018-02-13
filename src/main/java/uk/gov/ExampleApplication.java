package uk.gov;

import com.codahale.metrics.MetricRegistry;
import io.dropwizard.Application;
import io.dropwizard.configuration.EnvironmentVariableSubstitutor;
import io.dropwizard.configuration.SubstitutingSourceProvider;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.prometheus.client.CollectorRegistry;
import io.prometheus.client.dropwizard.DropwizardExports;
import io.prometheus.client.exporter.MetricsServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import uk.gov.ida.dropwizard.logstash.LogstashBundle;
import uk.gov.reng.metrics.config.Configuration;
import uk.gov.reng.metrics.filter.AuthenticationFilter;
import uk.gov.resources.HelloWorld;

import javax.servlet.DispatcherType;
import java.util.EnumSet;

public class ExampleApplication extends Application<ExampleConfiguration> {
    public static void main(String[] args) throws Exception {
        if (args == null || args.length == 0) {
            new ExampleApplication().run("server", System.getenv("CONFIG_FILE"));
        } else {
            new ExampleApplication().run(args);
        }
    }

    @Override
    public String getName() {
        return "dropwizard-example";
    }

    @Override
    public void initialize(final Bootstrap<ExampleConfiguration> bootstrap) {
        bootstrap.setConfigurationSourceProvider(
                new SubstitutingSourceProvider(bootstrap.getConfigurationSourceProvider(),
                        new EnvironmentVariableSubstitutor(false)
                )
        );
        bootstrap.addBundle(new LogstashBundle());
        MetricRegistry metrics = bootstrap.getMetricRegistry();
        CollectorRegistry.defaultRegistry.register(new DropwizardExports(metrics));
    }

    @Override
    public void run(final ExampleConfiguration configuration,
                    final Environment environment) {
        final Configuration conf = Configuration.getInstance();

        environment.jersey().register(new HelloWorld());
        environment.servlets().addServlet("prometheus", new MetricsServlet())
                .addMapping(conf.getPrometheusMetricsPath());

        environment.servlets()
                .addFilter("AuthenticationFilter", new AuthenticationFilter())
                .addMappingForUrlPatterns(
                        EnumSet.of(DispatcherType.REQUEST),
                        true,
                        conf.getPrometheusMetricsPath());
    }

}
