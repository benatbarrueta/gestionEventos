package es.deusto.spq.server;

import java.io.IOException;
import java.net.URI;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

/**
 * The Main class represents the entry point of the application.
 * It starts the HTTP server and provides methods to start and stop the server.
 */
public class Main {
    
    public static final String BASE_URI = "http://localhost:8080/rest/";

    /**
     * Starts the HTTP server with the configured resource packages.
     * 
     * @return The started HttpServer instance.
     */
    public static HttpServer startServer() {
        final ResourceConfig rc = new ResourceConfig().packages("es.deusto.spq.server");
        return GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc);
    }

    /**
     * The main method of the application.
     * It starts the HTTP server, prints the server information, and waits for user input to stop the server.
     * 
     * @param args The command line arguments.
     * @throws IOException If an I/O error occurs.
     */
    public static void main(String[] args) throws IOException {
        final HttpServer server = startServer();
        System.out.println(String.format("Jersey app started with WADL available at "
                + "%sapplication.wadl\nHit enter to stop it...", BASE_URI));
        System.in.read();
        server.shutdown();
    }
}
