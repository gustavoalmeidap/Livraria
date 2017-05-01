package br.com.caelum.livraria.bean;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

import br.com.caelum.livraria.dao.DAO;
import br.com.caelum.livraria.modelo.Autor;
import br.com.caelum.livraria.modelo.Livro;

@ViewScoped
@ManagedBean
public class LivroBean {

	private Livro livro = new Livro();
	private Integer autorId;

	public List<Autor> getAutores() {
		return new DAO<Autor>(Autor.class).listaTodos();
	}
	
	public void gravarAutor() {
		Autor autor = new DAO<Autor>(Autor.class).buscaPorId(autorId);
		this.livro.adicionaAutor(autor);

		List<Autor> autores = livro.getAutores();
	}
	
	public Integer getAutorId() {
		return autorId;
	}

	public void setAutorId(Integer autorId) {
		this.autorId = autorId;
	}

	public Livro getLivro() {
		return livro;
	}
	
	public void adicionaAutor(Autor autor) {
		this.livro.adicionaAutor(autor);
	}

	public void gravar() {
		System.out.println("Gravando livro " + this.livro.getTitulo());

		if (livro.getAutores().isEmpty()) {
			FacesContext.getCurrentInstance().addMessage("autor",
					new FacesMessage("Livro deve ter pelo menos um autor"));
		}

		if (livro.getId() == null) {
			new DAO<Livro>(Livro.class).adiciona(this.livro);
		} else {
			new DAO<Livro>(Livro.class).atualiza(this.livro);
		}
		
		this.livro = new Livro();
	}
	
	public void remover(Livro livro) {
		System.out.println("Removendo livro");
		new DAO<Livro>(Livro.class).remove(livro);
	}
	
	public void alterar(Livro livro) {
		System.out.println("Alterando livro");
		this.livro = livro;
	}
	
	public void removerAutorLivro(Autor autor) {
		this.livro.removeAutor(autor);
	}
	
	public List<Autor> getAutoresLivro() {
		return this.livro.getAutores();
	}
	
	public List<Livro> getTodosLivros() {
		return new DAO<Livro>(Livro.class).listaTodos();
	}
	
	public void comecaComDigitoUm (FacesContext fc, UIComponent ui, Object value) {
		String valor = value.toString();
		if (!valor.startsWith("1")) {
			throw new ValidatorException(new FacesMessage("O ISBN deve sempre começar com 1"));
		}
	}
	
	public void carregaLivroId() {
		this.livro = new DAO<Livro>(Livro.class).buscaPorId(livro.getId());
		if (this.livro == null) {
			this.livro = new Livro();
		}
	}

}
