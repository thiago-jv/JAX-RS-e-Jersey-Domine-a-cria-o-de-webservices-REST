package br.com.alura.loja;

import java.io.IOException;
import java.net.URI;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

public class Servidor {

	public static void main(String[] args) throws IOException {
		HttpServer server = InicializaServidor();
		System.out.println("Servidor rodando");
		System.in.read();
		server.stop();
	}

	public static HttpServer InicializaServidor() {
		// Configurando os resources
		ResourceConfig config = new ResourceConfig().packages("br.com.alura.loja");
		// Configurando URI
		URI uri = URI.create("http://localhost:8080/");
		// Subindo servidor
		HttpServer server = GrizzlyHttpServerFactory.createHttpServer(uri, config);
		return server;
	}

}
