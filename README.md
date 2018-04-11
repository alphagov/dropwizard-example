# dropwizard-example

How to start the dropwizard-example application
---

1. Make sure both [`dropwizard-logstash`](https://github.com/alphagov/dropwizard-logstash) and [`dropwizardPrometheus`](https://github.com/alphagov/gds_metrics_dropwizard) 
are at the right version in [`external-dependencies.gradle`](external-dependencies.gradle). If they are not published on Maven Central, you may want to publish them to
your Maven local repository.
1. Run `./gradlew build` to build the application
1. Start application with `./gradlew run`
1. To check that your application is running enter url `http://localhost:8080`
1. To deploy, run `cf push`

Health Check
---

To see your applications health enter url `http://localhost:8081/healthcheck`
