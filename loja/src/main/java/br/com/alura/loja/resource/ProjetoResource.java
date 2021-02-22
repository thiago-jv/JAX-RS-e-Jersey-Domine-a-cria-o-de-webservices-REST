package br.com.alura.loja.resource;

import java.net.URI;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;

import br.com.alura.loja.dao.CarrinhoDAO;
import br.com.alura.loja.dao.ProjetoDao;
import br.com.alura.loja.modelo.Carrinho;
import br.com.alura.loja.modelo.Produto;
import br.com.alura.loja.modelo.Projeto;

@Path("projetos")
public class ProjetoResource {

	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public String busca(@PathParam("id") long id) {
		Projeto projeto = new ProjetoDao().busca(1L);
		return projeto.toJson();
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response adiciona(String conteudo) {
		Projeto projeto = (Projeto) new Gson().fromJson(conteudo, Projeto.class);
		new ProjetoDao().adicinar(projeto);
		
		URI uri = URI.create("/projetos/" +projeto.getId());
		return Response.created(uri).build();
	}

	@Path("{id}")
	@DELETE
	public Response removeProduto(@PathParam("id") long id) {
    Projeto projeto = new ProjetoDao().busca(id);
    projeto = new ProjetoDao().remove(projeto.getId());
	return Response.noContent().build();
	}

}
