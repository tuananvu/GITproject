package syneren.sietech.registration;

import java.util.Date;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

import syneren.sietech.registration.ejb.eao.RegistrationEAO;

@ManagedBean(name="regBean")
@SessionScoped()
public class RegistrationBean {
	private String username;
	private String password;
	private String confirmpassword;
	private String firstname;
	private String lastname;
	private Date dob;
	private String email;
	private String confirmemail;
	private String[] selectedRoles;
		
	@EJB RegistrationEAO service;
	
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

	public String getConfirmpassword() {
		return confirmpassword;
	}

	public void setConfirmpassword(String confirmpassword) {
		this.confirmpassword = confirmpassword;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getConfirmemail() {
		return confirmemail;
	}

	public void setConfirmemail(String confirmemail) {
		this.confirmemail = confirmemail;
	}

	private void addMessage(FacesMessage message){
		FacesContext.getCurrentInstance().addMessage(null, message);
	}
	
	public String[] getSelectedRoles() {
		return selectedRoles;
	}

	public void setSelectedRoles(String[] selectedRoles) {
		this.selectedRoles = selectedRoles;
	}

	public void emailValidator(FacesContext context, UIComponent component, Object value){
		UIInput emailComponent = (UIInput)component.getAttributes().get("email");
		String email = (String) emailComponent.getValue();
		String confirmEmail = (String)value;
		if(email.indexOf("@")<0 || email.indexOf("@") == email.length()-1){
			throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid Email Address", null));
		}
		else if(!email.equals(confirmEmail)){
			throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid Email Address", null));
		}
	}
	public String addUser(){
		if(service.persistUser(this)){
			addMessage(new FacesMessage(FacesMessage.SEVERITY_INFO, "User registration successful", null));
			return "success";
		}
	    addMessage(new FacesMessage(FacesMessage.SEVERITY_ERROR, "User Registration Failed!!!", null));
	    return "failure";
	}

}
