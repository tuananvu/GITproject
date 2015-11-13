package syneren.sietech.registration;

import java.security.Principal;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

@ManagedBean(name="loginBean")
public class LoginBean {

	private String username;
	private String password;
	
	public LoginBean() {
		//HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		//if(session != null){
		//	session.invalidate();
		//}			
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String login(){
		String message = "";
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		try {
			request.login(username, password);
			Principal principal = request.getUserPrincipal();
			if(request.isUserInRole("Administrator")){
				message = "Username: " + principal.getName() + " - Administrator";
			}else if(request.isUserInRole("Manager")){
				message = "Username: " + principal.getName() + " - Manager";
			}else if(request.isUserInRole("Guest")){
				message = "Username: " + principal.getName() + " - Guest";
			}
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, message, null));
			return "success";
		} catch (ServletException e) {
			FacesContext.getCurrentInstance().addMessage(null,  new FacesMessage(FacesMessage.SEVERITY_ERROR, "Login failed", null));
			e.printStackTrace();
		}
		return "failure";
	}
	
	public String logout(){
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();				
		return "/faces/index.xhtml";
	}

}
