package View;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class URLFilter implements Filter {

    private FilterConfig filterConfig;
	
	public void destroy() {
		this.filterConfig = null;
	}
 
	public void doFilter(ServletRequest servletrequest,
			ServletResponse servletresponse, FilterChain filterchain)
			throws IOException, ServletException {
		FilterChain chain = filterchain;
		HttpServletRequest request = (HttpServletRequest) servletrequest;
		HttpServletResponse response = (HttpServletResponse) servletresponse;
        String url = request.getRequestURI();
        if (url == null) 
        {
        	url = "";
        }
		if (url.endsWith("jsp") || url.endsWith(".do")) {
			HttpSession session = request.getSession();
			String userId = (String) session.getAttribute("userID");
			if (noFileUrl(url, request)) {
				chain.doFilter(request, response);
			} else if (userId == null) {
				response.sendRedirect("../login.jsp");
			} else {
				request.setAttribute("user", session.getAttribute("userName"));
				request.setAttribute("ruleID", session.getAttribute("ruleID"));
				request.setAttribute("institutexx", session.getAttribute("institutexx"));
				chain.doFilter(request, response);
			} 
		}
		else {
			chain.doFilter(request, response);
		}
	}
	
	private void verifyUrl(String url, HttpServletRequest req,HttpServletResponse resp) throws ServletException, IOException {
        int lastSlash = url.lastIndexOf("/");
        String path = url.substring(lastSlash);
        String uri = "pages" + path;
        req.getRequestDispatcher(uri).forward(req, resp);
	}

	private boolean noFileUrl(String url, HttpServletRequest request2) {
		 if (url.indexOf("login.jsp") >= 0 || url.indexOf("login.do") >= 0) 
		 {
	        return true;
	     }
		return false;
	}

	public void init(FilterConfig filterconfig) throws ServletException {
		this.filterConfig = filterConfig;	
	}

}
