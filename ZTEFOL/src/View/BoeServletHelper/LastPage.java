package View.BoeServletHelper;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import Business.BusinessFactory;
import Business.Claims.BClaim;
import Business.UserDetail.BUserDetail;
import Common.CourseMngt;
import Common.FOLLogger;

public class LastPage extends BoeState {

	private static Logger logger = FOLLogger.getLogger(LastPage.class);

	private HttpSession mSession;

	private BUserDetail userDetail;

	private boolean submit = false;

	public LastPage(Page page, HttpServletRequest request, HttpServletResponse response) {
		super(page, request, response);
		init();
	}

	private void init() {
		BusinessFactory factory = BusinessFactory.getFactory();
		mSession = mRequest.getSession();
		userDetail = factory.getBUserDetailHanlder().getUserDetail((String) mSession.getAttribute("userID"),
				(String) mSession.getAttribute("userName"));
	}

	@Override
	public void enter() throws ServletException, IOException {
		mRequest.setAttribute("userInfo", userDetail);
		mRequest.setAttribute("claim", mSession.getAttribute("claim"));
		mRequest.setAttribute("submit", submit);

		int invoiceType = ((BClaim) mSession.getAttribute("claim")).getInvoiceType();
		if (invoiceType == 5) {
			mRequest.getRequestDispatcher("BoeWizardIndex4.jsp").forward(mRequest, mResponse);
		} else {
			mRequest.setAttribute("courseMap", CourseMngt.getInstance().getCourseMap());
			mRequest.getRequestDispatcher("BoeWizardIndex4Other.jsp").forward(mRequest, mResponse);
		}
	}

	@Override
	public void goNext() throws ServletException, IOException {
	}

	@Override
	public void goPre() throws ServletException, IOException {
		mPage.setPageState(PageState.Third);
	}

	@Override
	public void save() throws ServletException, IOException {
		BClaim claim = (BClaim) mSession.getAttribute("claim");
		boolean isSucess = BusinessFactory.getFactory().getClaimHandler().submit(claim);
		mRequest.setAttribute("isSucess", isSucess);
		if (isSucess) {
			submit = isSucess;
		}
		enter();
	}

	@Override
	public void refreshServlet() {

	}

}
