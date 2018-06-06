package View.BoeServletHelper;

import java.io.IOException;
import java.sql.Timestamp;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import Business.BusinessFactory;
import Business.Claims.BClaim;
import Business.UserDetail.BUserDetail;
import Common.ApprovalFlowMngt;
import Common.CourseMngt;
import Common.FOLLogger;
import Common.ProjectMngt;
import Common.Location.LocationMngt;
import Data.Project.DApprovalFlow;

public class FirstPage extends BoeState {
	private static Logger logger = FOLLogger.getLogger(FirstPage.class);
	private HttpSession mSession;

	private BUserDetail userDetail;

	private boolean mNeedCreate;

	public FirstPage(Page page, HttpServletRequest request, HttpServletResponse response) {
		super(page, request, response);
		init();
	}

	private void init() {
		BusinessFactory factory = BusinessFactory.getFactory();
		mSession = mRequest.getSession();
		userDetail = factory.getBUserDetailHanlder().getUserDetail((String) mSession.getAttribute("userID"),
				(String) mSession.getAttribute("userName"));
		mRequest.setAttribute("userInfo", userDetail);

		mNeedCreate = true;
	}

	public void goNext() throws ServletException, IOException {
		createOrSave();
		mPage.setPageState(PageState.Second);
	}

	public void goPre() {
	}

	public void save() throws ServletException, IOException {
		createOrSave();
		enter();
	}

	private void createOrSave() {
		if (mNeedCreate) {
			createClaim();
			mNeedCreate = false;
		} else {
			saveClaim();
		}
	}

	private void createClaim() {
		BClaim claim = new BClaim();
		claim.setNo(userDetail.getNo());
		claim.setProductId(Integer.parseInt(mRequest.getParameter("product")));
		claim.setSubProductId(Integer.parseInt(mRequest.getParameter("subProductId")));
		claim.setProductName(ProjectMngt.getProjectName(claim.getProductId()));
		claim.setSubProductName(ProjectMngt.getSubProjectMap2().get(claim.getSubProductId()));
		claim.setDeptId(userDetail.getDeptID());
		claim.setSubmitDate(new Timestamp(System.currentTimeMillis()));

		claim.setHasBill(Boolean.parseBoolean(mRequest.getParameter("isHasBill")));
		claim.setBillLoaction(Integer.parseInt(mRequest.getParameter("deliver_area")));
		claim.setDirectToFinance(Boolean.parseBoolean(mRequest.getParameter("directTofinance")));
		claim.setInvoiceType(Integer.parseInt(mRequest.getParameter("boe_type_id")));
		setApproval(claim);
		claim = BusinessFactory.getFactory().getClaimHandler().createClaim(claim);
		mSession.setAttribute("claim", claim);
		mRequest.setAttribute("claim", claim);
	}

	private void setApproval(BClaim claim) {
		DApprovalFlow flow = ApprovalFlowMngt.getActApprovalFlow(claim);
		claim.setApprovalId(flow.getApprovalid());
		claim.setApprovalName(flow.getApprovalname());

	}

	private void saveClaim() {
		BClaim claim = (BClaim) mSession.getAttribute("claim");
		claim.setProductId(Integer.parseInt(mRequest.getParameter("product")));
		claim.setProductName(ProjectMngt.getProjectName(claim.getProductId()));
		claim.setSubProductName(ProjectMngt.getProjectMap().get(claim.getSubProductId()));
		claim.setSubProductId(Integer.parseInt(mRequest.getParameter("subProductId")));
		setApproval(claim);
		claim.setHasBill(Boolean.parseBoolean(mRequest.getParameter("isHasBill")));
		claim.setBillLoaction(Integer.parseInt(mRequest.getParameter("deliver_area")));
		claim.setDirectToFinance(Boolean.parseBoolean(mRequest.getParameter("directTofinance")));
		claim.setInvoiceType(Integer.parseInt(mRequest.getParameter("boe_type_id")));
		mSession.setAttribute("claim", claim);
		claim = BusinessFactory.getFactory().getClaimHandler().saveClaim(claim);
	}

	@Override
	public void enter() throws ServletException, IOException {
		Integer curProject = 4; //公司管理
		Integer curSubProject = 1; 
		Integer curInvoiceType = 5; //差旅费
		Integer curlocation = 1;
		BClaim  curClaim = null;
		curClaim = (BClaim)mSession.getAttribute("claim");
				
		mRequest.setAttribute("userInfo", userDetail);
		mRequest.setAttribute("cityInfo", LocationMngt.getInstance().getCityMap());
		mRequest.setAttribute("courseMap", CourseMngt.getInstance().getCourseMap());
		mRequest.setAttribute("prodInfo", ProjectMngt.getProjectMap());
		mRequest.setAttribute("subProducts", ProjectMngt.getSubProjectMap());
		if(curClaim != null)
		{
			curProject = curClaim.getProductId();
			curSubProject = curClaim.getSubProductId();
			curInvoiceType = curClaim.getInvoiceType();
			curlocation = curClaim.getBillLoaction();
		}
		
		mRequest.setAttribute("curProject", curProject);
		mRequest.setAttribute("curSubProject", curSubProject);
		mRequest.setAttribute("curInvoiceType", curInvoiceType);
		mRequest.setAttribute("curlocation", curlocation);
			
		mRequest.getRequestDispatcher("BoeWizardIndex.jsp").forward(mRequest, mResponse);
	}

	@Override
	public void refreshServlet() {
		mRequest.setAttribute("userInfo", userDetail);
	}

}
