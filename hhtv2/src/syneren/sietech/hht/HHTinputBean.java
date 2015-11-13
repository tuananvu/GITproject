package syneren.sietech.hht;


import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import javax.servlet.jsp.JspException;

@ManagedBean(name="hhtinputBean")
@RequestScoped
public class HHTinputBean {
	private Part file;
	private String fileContent;
	private String colorChannel;
	private String emd;
	private String iterationNo;
	private String windowSize;
	private String threshold;
	private String hsa;
	private String chip;
	private final static Logger LOGGER = Logger.getLogger(HHTinputBean.class.getCanonicalName());
	private String fileName;
	private List<Integer> iterationList = new ArrayList<Integer>();
	
	public List<Integer> getIterationList() {
		return iterationList;
	}
	public void setIterationList(List<Integer> iterationList) {
		this.iterationList = iterationList;
	}
	
	public String getFileName() {
		return fileName;
	}
	
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getColorChannel() {
		return colorChannel;
	}
	public void setColorChannel(String colorChannel) {
		this.colorChannel = colorChannel;
	}
	public String getEmd() {
		return emd;
	}
	public void setEmd(String emd) {
		this.emd = emd;
	}
	public String getIterationNo() {
		return iterationNo;
	}
	public void setIterationNo(String iterationNo) {
		this.iterationNo = iterationNo;
		for(int i=1; i <= Integer.parseInt(iterationNo); i++){
			iterationList.add(i);
		}
	}
	public String getWindowSize() {
		return windowSize;
	}
	public void setWindowSize(String windowSize) {
		this.windowSize = windowSize;
	}
	public String getThreshold() {
		return threshold;
	}
	public void setThreshold(String threshold) {
		this.threshold = threshold;
	}
	public String getHsa() {
		return hsa;
	}
	public void setHsa(String hsa) {
		this.hsa = hsa.equals("true")?"1":"0";
	}
	public String getChip() {
		return chip;
	}
	public void setChip(String chip) {
		this.chip = chip;
	}
	public Part getFile() {
		return file;
	}
	public void setFile(Part file) {
		this.file = file;
	}
	public String getFileContent() {
		return fileContent;
	}
	public void setFileContent(String fileContent) {
		this.fileContent = fileContent;
	}
	public HHTinputBean(){
		
	}
//	public String upload(){
//		try {
//			fileContent = new Scanner(file.getInputStream()).useDelimiter("\\A").next();
//			System.out.println("scanned file: " + fileContent);
//			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "scanned", null));
//		} catch (Exception e) {
//			e.printStackTrace();
//			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "error", null));
//		}
//		return "abc";
//	}
	
	public String upload(){
//		 boolean jsf22Supported = false;
//	        if(! jsf22Supported){
//	            String errMsg = "jsf22 not supported <h:inputFile />)!";
//	            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, errMsg, null));
//	            return "error";
//	        }
	     String fileName = getFileName(getFile());
	     try{
	            //getFile().write("C://upload//"+fileName);
	            fileContent = new Scanner(file.getInputStream()).useDelimiter("\\A").next();
	            //System.out.println("scanned file: " + fileContent);
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "scanned", null));
	            
	        }catch(Exception e){
	            String errMsg = e.getMessage();
	            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, errMsg, null));
	            return "error";
	        }
	     return "success";
	}
	 private String getFileName(final Part part) {
	        for (String content : part.getHeader("content-disposition").split(";")) {
	            if (content.trim().startsWith("filename")) {
	                return content.substring(content.indexOf('=') + 1).trim()
	                        .replace("\"", "");
	            }
	        }
	        return null;
	    }
	
	 public String processHHT(){
		 fileName = getFileNameWithoutExtension(file);
		 final String path = "C:\\Users\\anguyen\\Documents\\WORKING\\test\\HHT2\\";
		 OutputStream out = null;
			InputStream fileContent = null;
			// final PrintWriter writer = response.getWriter();
			try {
				out = new FileOutputStream(new File(path + File.separator
						+ fileName + ".bmp"));
				fileContent = file.getInputStream();
				int read = 0;
				final byte[] bytes = new byte[1024];
				while ((read = fileContent.read(bytes)) != -1) {
					out.write(bytes, 0, read);
				}
				// writer.println("New file " + fileName + " created at " + path);
				
				LOGGER.log(Level.INFO, "File {0} being uploaded to {1}",
						new Object[] { fileName, path });
				if (out != null) {
					out.flush();
					out.close();
				}
				if (fileContent != null) {
					fileContent.close();
				}
			} catch (FileNotFoundException fne) {
				// writer.println("You either did not specify a file to upload or are trying to upload a file "
				// + "to a protected or nonexistent location.");
				// writer.println("<br/> ERROR: " + fne.getMessage());
				LOGGER.log(Level.SEVERE, "Problems during file upload. Error: {0}",
						new Object[] { fne.getMessage() });
				return "failure";
			} catch(IOException ioe){
				LOGGER.log(Level.SEVERE, "Problems with file I/O. Error: {0}",
						new Object[] { ioe.getMessage() });
				return "failure";
			}			
			String batFile = "C:\\Users\\anguyen\\Documents\\WORKING\\test\\HHT2\\run_EXE_GPU_OpenCV_HHT2.bat "//"/home/synuser/HHT2/run_CPU_HHT2_EMD2_HSA2.sh " //"C:\\Users\\anguyen\\Documents\\WORKING\\test\\HHT2\\run_CPU_HHT2_EMD2_HSA2.bat "
					+ fileName + ".bmp "
					+ fileName + " "
					+ colorChannel + " "
					+ emd + " "
					+ iterationNo + " "
					+ windowSize + " "
					+ threshold + " "
					+ hsa + " "
					+ chip + " ";
					
					
			java.lang.Process process;			
			
			try {
				process = Runtime.getRuntime().exec(batFile);
			// any error message?
	        StreamGobbler errorGobbler = new 
	            StreamGobbler(process.getErrorStream(), "ERROR");            
	        
	        // any output?
	        StreamGobbler outputGobbler = new 
	            StreamGobbler(process.getInputStream(), "OUTPUT");
	            
	        // kick them off
	        errorGobbler.start();
	        outputGobbler.start();
				process.waitFor();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return "failure";
			} catch(IOException ioe){
				LOGGER.log(Level.SEVERE, "Problems with file I/O. Error: {0}",
						new Object[] { ioe.getMessage() });
				return "failure";
			}	
			System.out.println(batFile);
		 return "success";
	 }
	 
	 private String getFileNameWithoutExtension(final Part part) {
			String vfileName = getFileName(part);
			int extensionIndex = vfileName.lastIndexOf('.');
			int pathIndex = vfileName.lastIndexOf('\\');

			if (extensionIndex > 0 && extensionIndex <= vfileName.length() - 2) {
				if (pathIndex > -1)
					return vfileName.substring(pathIndex + 1, extensionIndex);
				else
					return vfileName.substring(0, extensionIndex);
			}
			return null;
		}
//	 public void genIMG() throws JspException, IOException{				
//		 	//String fileName = getFileName(part);
//		 	HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
//			
//		 	ServletOutputStream outputStream = pageContext.getResponse().getOutputStream();
//			OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream, "UTF-8");
//			BufferedInputStream bufferedInputStream;
//			byte abyte0[] = new byte[4096];
//			int bytesRead;
//			bufferedInputStream = new BufferedInputStream(new FileInputStream(new File(fileName)));
//			while((bytesRead = bufferedInputStream.read(abyte0, 0, 4096)) != -1){
//				outputStream.write(abyte0, 0, bytesRead);			
//			}
//			
//			outputStreamWriter.flush();
//			outputStreamWriter.close();
//			outputStream.flush();
//			outputStream.close();
//			
//			bufferedInputStream.close();
//				
//					
//		}
}
class StreamGobbler extends Thread
{
    InputStream is;
    String type;
    
    StreamGobbler(InputStream is, String type)
    {
        this.is = is;
        this.type = type;
    }
    
    public void run()
    {
        try
        {
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String line=null;
            while ( (line = br.readLine()) != null)
                System.out.println(type + ">" + line);    
            } catch (IOException ioe)
              {
                ioe.printStackTrace();  
              }
    }
}
