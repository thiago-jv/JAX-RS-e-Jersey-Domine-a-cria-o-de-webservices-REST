package br.com.alura.loja;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.filter.LoggingFilter;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.thoughtworks.xstream.XStream;

import br.com.alura.loja.modelo.Carrinho;
import br.com.alura.loja.modelo.Produto;

public class ClienteTeste {

	private HttpServer server;
	
	private Client client;
	
	private WebTarget target;
	
	@Before
	public void StartaServidor() {
	 server	= Servidor.InicializaServidor();
	 
	 // registrando os filtros com Client
	 ClientConfig config = new ClientConfig();
	 // Uzando a API de log de LogFilter
	 config.register(new LoggingFilter());
	 
	 this.client = ClientBuilder.newClient(config);
	// Origem
	 this.target = client.target("http://localhost:8080");
	}
	
	@After
	public void mataServidor() {
		server.stop();
	}

	@Test
	public void testaQueBuscarUmCarrinhoTrazOCarrinhoEsperado() {
		// Retorna o conteudo com passando o Path por meio de um endPoint GET
		String conteudo = this.target.path("/carrinhos/1").request().get(String.class);
		// Verificica se existe o conteudo passado
		Assert.assertTrue(conteudo.contains("<rua>Rua Vergueiro 3185"));

		// Garantido a desserialização
		Carrinho carrinho = (Carrinho) new XStream().fromXML(conteudo);
		// Verificando se a rua passada, é igula ao cadastro
		Assert.assertEquals("Rua Vergueiro 3185, 8 andar", carrinho.getRua());
	}
	
	@Test
    public void testaQueSuportaNovosCarrinhos(){
        Carrinho carrinho = new Carrinho();
        carrinho.adiciona(new Produto(314L, "Tablet", 999, 1));
        carrinho.setRua("Rua Vergueiro");
        carrinho.setCidade("Sao Paulo");
        String json = carrinho.toJson();

        Entity<String> entity = Entity.entity(json, MediaType.APPLICATION_JSON);
        
        Response response = this.target.path("/carrinhos").request().post(entity);
        // Valida de o retorno do Status é 201
        Assert.assertEquals(201, response.getStatus());
        
        // retorna a Location -> com path do caminho
        String location = response.getHeaderString("Location");
        
        //Retorna o conteudo do carrinho
        String conteudo = client.target(location).request().get(String.class);
        // valida se dentro do conteudo temos o Microfone
        Assert.assertTrue(conteudo.contains("Microfone"));
        
        
    }
}
