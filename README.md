# dropwizard-example

How to start the dropwizard-example application
---

1. Push the Prometheus dependency to your local Maven repository - see README here [`dropwizardPrometheus`](https://github.com/alphagov/gds_metrics_dropwizard)
1. Run `./gradlew build` to build the application
1. Start application with `java -jar target/dropwizard-example-1.0-SNAPSHOT.jar server config.yml`
1. To check that your application is running enter url `http://localhost:8080`
1. To deploy, run `cf push`

Health Check
---

To see your applications health enter url `http://localhost:8081/healthcheck`
