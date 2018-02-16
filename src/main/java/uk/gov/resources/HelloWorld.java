package uk.gov.resources;

import io.prometheus.client.Counter;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/")
public class HelloWorld {

	static final Counter requests1 = Counter.build()
			.name("hello_requests_total")
			.labelNames("a1")
			.help("Total requests.").register();

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String helloWorld() {
		requests1.labels("a1").inc();
		requests1.labels("a2").inc();
		requests1.labels("a3").inc();
		requests1.labels("a4").inc();

		return "Hello, world!";
	}
}
