package tk.blizz.ssh.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import javax.servlet.ServletContext;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;

import org.apache.struts2.ServletActionContext;

import tk.blizz.ssh.model.User;

import com.opensymphony.xwork2.ActionSupport;

public class ReportAction extends ActionSupport {
	protected List<User> users;
	protected String reportName;

	private static final String FILEPATTERN = "^[a-zA-Z][a-zA-Z0-9_]*$";

	private final Pattern filePattern = Pattern.compile(FILEPATTERN);

	protected File getReportDir() {
		ServletContext context = ServletActionContext.getServletContext();
		StringBuilder sb = new StringBuilder(128);

		sb.append(context.getRealPath(context.getContextPath()));
		sb.append("/reports");

		return new File(sb.toString());
	}

	protected void checkCompiledReport() throws IOException {
		// jrxml
		File file = new File(getReportDir().getAbsolutePath(), this.reportName
				+ ".xml");

		File out = new File(getReportDir(), this.reportName + ".jasper");

		if (!file.exists())
			throw new IOException(new FileNotFoundException(
					file.getAbsolutePath()));

		if (out.exists())
			return;

		try {
			JasperCompileManager.compileReportToFile(file.getAbsolutePath(),
					out.getAbsolutePath());
		} catch (JRException e) {
			throw new IOException(e);
		}

	}

	@Override
	public String execute() throws IOException {
		// checkCompiledReport();

		this.users = new ArrayList<User>();
		this.users.add(new User().setId(1L).setName("John")
				.setLastName("Smith"));
		this.users.add(new User().setId(2L).setName("John2")
				.setLastName("Smith2").setBirthday(new Date()));
		this.users.add(new User().setId(3L).setName("John3")
				.setLastName("Smith3"));
		return SUCCESS;
	}

	@Override
	public void validate() {
		if (this.reportName == null || this.reportName.isEmpty()) {
			throw new IllegalArgumentException("need reportName parameter");
		}

		if (!this.filePattern.matcher(this.reportName).matches()) {
			throw new IllegalArgumentException("invalid reportName parameter");
		}

	}

	public String getReportLocation() {
		File file = new File("/WEB-INF/reports/", this.reportName + ".jasper");
		return file.getAbsolutePath();
	}

	public List<User> getDatasource() {
		return this.users;
	}

	public InputStream getReport() throws Exception {
		File file = new File("/", this.reportName + ".jasper");

		InputStream is = new FileInputStream(file);
		return is;
	}

	// setters
	public void setReportName(String reportName) {
		this.reportName = reportName;
	}

}
