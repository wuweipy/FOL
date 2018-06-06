package Business.Claims;

import Common.ApprovalFlowMngt;
import Data.Claims.ClaimsDAO;
import Data.Claims.ClaimsItem;
import Data.Claims.ClaimsItemDao;
import Data.Claims.DClaim;
import Data.Claims.DClaimFactory;
import Data.Claims.IClaimsDAO;
import Data.Project.ApprovalFlowDAO;
import Data.Project.DApprovalFlow;

public class ClaimHandler {

	public BClaim createClaim(BClaim bClaim) {
		DClaim claim = new DClaimFactory().getClaim(bClaim.getNo());
		claim.setHasBill(bClaim.isHasBill());
		claim.setDeptId(bClaim.getDeptId());
		claim.setSubmitDate(bClaim.getSubmitDate());
		claim.setProductId(bClaim.getProductId());
		claim.setSubProductId(bClaim.getSubProductId());
		claim.setBillLoaction(bClaim.getBillLoaction());
		claim.setDirectToFinance(bClaim.isDirectToFinance());
		claim.setApprovalId(bClaim.getApprovalId());
		claim.setInvoiceType(bClaim.getInvoiceType());
		IClaimsDAO claimsDAO = new ClaimsDAO();
		bClaim.setInvoiceNo(claim.getInvoiceNo());
		bClaim.setBillNo(claim.getBillNo());
		if (claimsDAO.createClaim(claim)) {
			return bClaim;
		}
		return null;
	}

	public BClaim saveClaim(BClaim claim) {
		int invoiceType = claim.getInvoiceType();
		DClaim dclaim = new DClaim();
		dclaim.setInvoiceNo(claim.getInvoiceNo());
		dclaim.setApprovalId(claim.getApprovalId());
		dclaim.setSummary(claim.getSummary());
		dclaim.setProductId(claim.getProductId());
		dclaim.setSubProductId(claim.getSubProductId());
		dclaim.setAccountAdjust(claim.getAccountAdjust());
		dclaim.setEmployLevel(claim.getEmployLevel());
		dclaim.setHedgeAccount(claim.getHedgeAccount());
		dclaim.setInvoiceType(invoiceType);
		dclaim.setHasBill(claim.isHasBill());
		dclaim.setBillLoaction(claim.getBillLoaction());
		if (invoiceType == 5) {
			dclaim.setTotalFee(claim.getTotalFee());
		} else {
			dclaim.setTotalFee(claim.getTotalFeeOther());
		}
		IClaimsDAO claimsDAO = new ClaimsDAO();

		if (invoiceType == 5) {
			if (claimsDAO.saveClaim(dclaim) && saveClaimItems(claim.getItems())) {
				return claim;
			}
		} else {
			if (claimsDAO.saveClaim(dclaim)) {
				return claim;
			}
		}
		return null;
	}

	private boolean saveClaimItems(BClaimItem[] items) {
		if (items == null)
			return true;
		ClaimsItem[] ditems = new ClaimsItem[items.length];
		for (int i = 0; i < items.length; i++) {
			ClaimsItem item = new ClaimsItem();
			BClaimItem bItem = items[i];
			item.setStartDate(bItem.getStartDate());
			item.setEndDate(bItem.getEndDate());
			item.setStartCity(bItem.getStartCity());
			item.setDesProvince(bItem.getDesProvince());
			item.setDesCity(bItem.getDesCity());
			item.setTransportation(bItem.getTransportation());
			item.setTransportCost(bItem.getTransportCost());
			item.setAccommodation(bItem.getAccommodation());
			item.setInvoinceNO(bItem.getInvoinceNO());
			item.setOtherCost(bItem.getOtherCost());
			ditems[i] = item;
		}
		ClaimsItemDao dao = new ClaimsItemDao();
		return dao.createItems(ditems);
	}

	public boolean savePayMode(int payMode, String invoiceNo) {
		return new ClaimsDAO().savePayMode(payMode, invoiceNo);
	}

	public boolean submit(BClaim claim) {
		DApprovalFlow flow = ApprovalFlowMngt.getActApprovalFlow(claim);
		return (new ClaimsDAO()).submit(claim.getInvoiceNo(), flow);
	}
}
