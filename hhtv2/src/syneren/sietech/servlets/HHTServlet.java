package syneren.sietech.servlets;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

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

/**
 * Servlet implementation class HHTServlet
 */
@WebServlet("/HHTServlet")
@MultipartConfig 
public class HHTServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final static Logger LOGGER = Logger.getLogger(HHTServlet.class.getCanonicalName());  
    /**
     * @see HttpServlet#HttpServlet()
     */
    public HHTServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html; charset=UTF-8");
		
		String colorChannel = request.getParameter("color_channel");
		String emd = request.getParameter("emd");
		String iterationNo = request.getParameter("iterationNo");
		String windowSize = request.getParameter("windowSize");
		String threshold = request.getParameter("threshold");
		String hsa = request.getParameter("chk_HSA");
		String chip = request.getParameter("chip");
		
		final String path = "C:\\Users\\anguyen\\Documents\\WORKING\\test\\HHT2\\";// "C:\\Users\\anguyen\\Documents\\WORKING\\RemoteDesktop\\UserInterfaceDesktop\\trunk\\Demo\\Library\\";
		final Part filePart = request.getPart("file");
		String fileName = getFileNameWithoutExtension(filePart);// getFileName(filePart);//sessionId
																// + ".bmp";//
																// getFileName(filePart);
		if (fileName == null || fileName.equals(""))
		{
			throw new ServletException();
		}
				
		OutputStream out = null;
		InputStream fileContent = null;
		// final PrintWriter writer = response.getWriter();
		try {
			out = new FileOutputStream(new File(path + File.separator
					+ fileName + ".bmp"));
			fileContent = filePart.getInputStream();
			int read = 0;
			final byte[] bytes = new byte[1024];
			while ((read = fileContent.read(bytes)) != -1) {
				out.write(bytes, 0, read);
			}
			// writer.println("New file " + fileName + " created at " + path);
			
			LOGGER.log(Level.INFO, "File {0} being uploaded to {1}",
					new Object[] { fileName, path });
		} catch (FileNotFoundException fne) {
			// writer.println("You either did not specify a file to upload or are trying to upload a file "
			// + "to a protected or nonexistent location.");
			// writer.println("<br/> ERROR: " + fne.getMessage());
			LOGGER.log(Level.SEVERE, "Problems during file upload. Error: {0}",
					new Object[] { fne.getMessage() });

		} finally {
			if (out != null) {
				out.flush();
				out.close();
			}
			if (fileContent != null) {
				fileContent.close();
			}
			// if(writer != null){
			// writer.close();
			// }
		}

		// Simple simple = (Simple)session.getAttribute("simple");
		// if(simple == null)
		// {
		// simple = new Simple(1, "NEW");
		// }
		// simple.setMessage(message);
		// session.setAttribute("simple", simple);

		// String batFile =
		// "C:\\Users\\anguyen\\Documents\\WORKING\\RemoteDesktop\\UserInterfaceDesktop\\trunk\\Demo\\Library\\run_cimg_imread.bat "
		// + windowSize + " " + iterationNo + " " + fileName + " " + sessionId;

//		String batFile = "C:\\Users\\anguyen\\Documents\\WORKING\\test\\HHT2\\run_cimg_imread.bat "
//				+ windowSize
//				+ " "
//				+ iterationNo
//				+ " "
//				+ fileName + ".bmp "
//				+ fileName + " "
//				+ "imf_1.bmp";
		
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
		try {
			process.waitFor();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(batFile);
		// response.sendRedirect("/test/ImageServlet");
		request.setAttribute("processed", true);
		request.setAttribute("fileName", fileName);
		
		RequestDispatcher rd = getServletContext().getRequestDispatcher(
				"/faces/guest/hhtresult.xhtml");
		rd.forward(request, response);
		// response.sendRedirect("/test/index.jsp");
		return;
	}
	
	private String getFileNameWithoutExtension(final Part part) {
		String fileName = getFileName(part);
		int extensionIndex = fileName.lastIndexOf('.');
		int pathIndex = fileName.lastIndexOf('\\');

		if (extensionIndex > 0 && extensionIndex <= fileName.length() - 2) {
			if (pathIndex > -1)
				return fileName.substring(pathIndex + 1, extensionIndex);
			else
				return fileName.substring(0, extensionIndex);
		}
		return null;
	}
	private String getFileName(final Part part) {
		final String partHeader = part.getHeader("content-disposition");
		LOGGER.log(Level.INFO, "Part Header = {0}", partHeader);
		for (String content : part.getHeader("content-disposition").split(";")) {
			if (content.trim().startsWith("filename")) {
				return content.substring(content.indexOf('=') + 1).trim()
						.replace("\"", "");
			}
		}
		return null;
	}
}