package syneren.sietech.hht;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.servlet.http.Part;

public class FileValidator implements Validator {

	public FileValidator() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void validate(FacesContext context, UIComponent component, Object value)
			throws ValidatorException {
		Part file = (Part) value;
        
        if (file.getSize() > 5000000){
        	throw new ValidatorException(new FacesMessage("File size " + file.getSize() + ", too big."));
        }       

	}

}
