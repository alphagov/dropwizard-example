package uk.gov;

import io.dropwizard.Application;
import io.dropwizard.configuration.EnvironmentVariableSubstitutor;
import io.dropwizard.configuration.SubstitutingSourceProvider;
import io.dropwizard.metrics5.MetricRegistry;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.prometheus.client.CollectorRegistry;
import io.prometheus.client.dropwizard.DropwizardExports;
import io.prometheus.client.exporter.MetricsServlet;
import io.prometheus.client.filter.MetricsFilter;
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
//		bootstrap.addBundle(new LogstashBundle());
		MetricRegistry metrics = bootstrap.getMetricRegistry();
		CollectorRegistry.defaultRegistry.register(new DropwizardExports(metrics));
	}

	@Override
	public void run(final ExampleConfiguration configuration,
					final Environment environment) {
		environment.jersey().register(new HelloWorld());
		environment.servlets().addServlet("metrics", new MetricsServlet()).addMapping("/metrics");

		double[] bucket = { 0.005, 0.01, 0.025, 0.05, 0.075, 0.1, 0.25, 0.5, 0.75, 1, 2.5, 5, 7.5, 10};
		environment.servlets()
				.addFilter("MetricsFilter", new MetricsFilter(
						"webapp_metrics_filter",
						"Help Text", 0,
						bucket)
				)
				.addMappingForUrlPatterns(
						EnumSet.of(DispatcherType.REQUEST),
						true, "/*");
	}

}
