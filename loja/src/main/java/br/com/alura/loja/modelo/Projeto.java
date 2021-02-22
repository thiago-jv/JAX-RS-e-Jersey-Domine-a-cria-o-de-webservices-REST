package br.com.alura.loja.modelo;

import com.google.gson.Gson;
import com.thoughtworks.xstream.XStream;

public class Projeto {

	private Long id;
	private String nome;
	private int anoDeIncio;

	public Projeto() {
	}

	public Projeto(Long id, String nome, int anoDeIncio) {
		this.id = id;
		this.nome = nome;
		this.anoDeIncio = anoDeIncio;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public int getAnoDeIncio() {
		return anoDeIncio;
	}

	public void setAnoDeIncio(int anoDeIncio) {
		this.anoDeIncio = anoDeIncio;
	}
	
	// Gera e retorna XML
	public String toXML() {
		return new XStream().toXML(this);
	}
	
	// Gera e retorna JSON
	public String toJson() {
		return new Gson().toJson(this);
	}
}
