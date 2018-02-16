package uk.gov.resources;

import io.prometheus.client.Counter;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/")
public class HelloWorld {

	static final Counter requests = Counter.build()
			.name("requests_total")
			.labelNames("a1", "a2")
			.help("Total requests.").register();

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String helloWorld() {
		requests.inc();

		return "Hello, world!";
	}
}
