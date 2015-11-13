package syneren.sietech.registration.validator;


import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

public class PasswordValidator implements Validator {

	public PasswordValidator() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void validate(FacesContext context, UIComponent component, Object value)
			throws ValidatorException {
		UIInput passwordComponent = (UIInput)component.getAttributes().get("password");
		String password = (String) passwordComponent.getValue();
		String confirm = (String) value;
		if(!password.equals(confirm)){
			throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Passwords dont match!!!", null));
		}
	}

}
