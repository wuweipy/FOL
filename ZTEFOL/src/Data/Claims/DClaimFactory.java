 package Data.Claims;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

import Common.FOLLogger;

public class DClaimFactory {
	private static Logger logger = FOLLogger.getLogger(DClaimFactory.class);
	public DClaimFactory() {
		init();
	}

	private void init() {

	}

	public DClaim getClaim(String no) {
		DClaim claim = new DClaim();
		claim.setNo(no);
		claim.setInvoiceNo(createInvoiceNo(no));
		claim.setBillNo(createBillNo(no));
		claim.setHasBill(false);
		claim.setProductId(-1);
		return claim;  
	}

	private String createBillNo(String no) {
		return no + System.currentTimeMillis();
	}

	private static synchronized String createInvoiceNo(String no) {

		// return no + System.currentTimeMillis();
		// ���ı������� �����ʱ��
		// �����ڵ�ʱ��ϲ�
		// �޸��ı�����
		// ���ȥ �ı������ݿ�
		String file = DClaimFactory.class.getResource("").toString();
		
//		file = file.substring(6) + "id.txt";           //windowsϵͳ
		file = "/" + file.substring(6) + "id.txt";      //linuxϵͳ
		
		System.out.println(file);
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			String data = br.readLine();
			br.close();
			String getDatas[] = data.split(",");
			String getDate = getDatas[0];
			String getId = getDatas[1]; 

			// ȡ����������
			Date currentTime = new Date();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			String dateString = formatter.format(currentTime);
			dateString = dateString.replace("-", "");
			// System.out.println(dateString);

			String saveDate = "";
			int saveId = 1;
			if (dateString.equals(getDate)) {
				saveId = Integer.parseInt(getId) + 1;
				DecimalFormat ndf = new DecimalFormat("000");
				String nsaveId = ndf.format(saveId);
				saveDate = dateString;
				saveDate = saveDate.replace("-", "");
				// ��ϴ�����ݿ����
				// �����д��txt
				String saveResult = saveDate + "," + saveId;
				BufferedWriter writer = new BufferedWriter(new FileWriter(
						new File(file)));				
				writer.write(saveResult);
				writer.close();
				System.out.println(saveDate + nsaveId);
				return saveDate + nsaveId;
			} else {
				saveId = 1;
				DecimalFormat ndf = new DecimalFormat("000");
				String nsaveId = ndf.format(saveId);
				saveDate = dateString;
				saveDate = saveDate.replace("-", "");
				// ��ϴ�����ݿ����
				// �����д��txt
				String saveResult = saveDate + "," + saveId;
				BufferedWriter writer = new BufferedWriter(new FileWriter(
						new File(file)));
				writer.write(saveResult);
				writer.close();
				System.out.println(saveDate + nsaveId);
				return saveDate + nsaveId;
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		return null;
//		return no + System.currentTimeMillis();
	}

	public static void main(String[] args) {
		DClaimFactory factory = new DClaimFactory();
		// System.out.println(factory.createBillNo());
	}

}

