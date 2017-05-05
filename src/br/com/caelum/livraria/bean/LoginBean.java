package br.com.caelum.livraria.bean;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import br.com.caelum.livraria.dao.UsuarioDAO;
import br.com.caelum.livraria.modelo.Usuario;

@ViewScoped
@ManagedBean
public class LoginBean {

	Usuario usuario = new Usuario();

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	public String efetuaLogin() {
		UsuarioDAO dao = new UsuarioDAO();
		
		FacesContext context = FacesContext.getCurrentInstance();
		
		if (dao.existeUsuario(this.usuario)) {
			context.getExternalContext().getSessionMap().put("usuarioLogado", this.usuario);
			return "livro?faces-redirect=true";
		} else {
			context.addMessage(null, new FacesMessage("Usuário não encontrado ou senha incorreta!"));
			context.getExternalContext().getFlash().setKeepMessages(true);
			return "login?faces-redirect=true";
		}
	}
	
	public String efetuaLogout() {
		FacesContext context = FacesContext.getCurrentInstance();
		
		context.getExternalContext().getSessionMap().remove("usuarioLogado");
		
		return "login?faces-redirect=true";
	}
}
