package syneren.sietech.hht;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

@ManagedBean(name="exceptionHandlingBean")
@RequestScoped
public class ExceptionHandlingBean {
	private Integer errorCode;
	private String requestUri;
	
	public Integer getErrorCode() {
		return errorCode;
	}


	public void setErrorCode(Integer errorCode) {
		this.errorCode = errorCode;
	}


	public String getRequestUri() {
		return requestUri;
	}


	public void setRequestUri(String requestUri) {
		this.requestUri = requestUri;
	}	
	 
	public ExceptionHandlingBean() {
		
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		
//		Throwable throwable = (Throwable) request
//                .getAttribute("javax.servlet.error.exception");
        errorCode = (Integer) request
                .getAttribute("javax.servlet.error.status_code");
//        String servletName = (String) request
//                .getAttribute("javax.servlet.error.servlet_name");
//        if (servletName == null) {
//            servletName = "Unknown";
//        }
        requestUri = (String) request
                .getAttribute("javax.servlet.error.request_uri");
        if (requestUri == null) {
            requestUri = "Unknown";
        }
        
	}

}
