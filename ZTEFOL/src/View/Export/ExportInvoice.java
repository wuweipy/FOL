package View.Export;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Colour;
import jxl.format.UnderlineStyle;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

public class ExportInvoice extends HttpServlet {

	/**
	 * @Fields serialVersionUID :
	 */
	private static final long serialVersionUID = 1L;

	public static String downloadPath = "";

	public void init(ServletConfig config) throws ServletException {
		downloadPath = config.getServletContext().getRealPath("/")
				+ "download/";
		super.init(config);
	}
	
	@Override
	protected void service(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String ms_code = "invoice";
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		String myarray = request.getParameter("myArray");
		String colums = request.getParameter("colums");
		String rows = request.getParameter("rows");
		myarray = myarray.replaceAll("\t", "");
		myarray = myarray.replaceAll("\b", "");
		myarray = myarray.replaceAll("\n", "");
		
		/* ���ɱ���ʼ */
		exportExcel(downloadPath+ms_code+".xls",ms_code,myarray,colums,rows);
		/* ���ɱ����� */
		
		ms_code = new String(ms_code.getBytes("utf-8"));
		File source = new File(downloadPath + ms_code + ".xls");
		response.reset();
		response.setContentType("application/force-download");
		response.setHeader("Content-Disposition", "attachment; filename=\""
				+ source.getName() + "\"");
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.getWriter().write("invoice.xls");
	}

	public static void exportExcel(String fileName, String ms_code,String myArray,String colums,String rows) {
		WritableWorkbook wwb;
		FileOutputStream fos;
		try {
			fos = new FileOutputStream(fileName);
			wwb = Workbook.createWorkbook(fos);
			int col = Integer.valueOf(colums);
			// ����һ��������
			WritableSheet ws = wwb.createSheet(ms_code, 10);
			// ���õ�Ԫ������ָ�ʽ
			WritableFont wf = new WritableFont(WritableFont.ARIAL, col,
					WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE,
					Colour.BLUE);
			WritableCellFormat wcf = new WritableCellFormat(wf);
			wcf.setVerticalAlignment(VerticalAlignment.CENTRE);
			wcf.setAlignment(Alignment.CENTRE);
			ws.setRowView(1, 500);

			// ������ݵ�����
			StringBuffer sb = new StringBuffer();
			String[] dates = myArray.split(",");

			// ������ͷ     ���ݺ�,��������,������,������Ŀ,�ύ����,��������,����״̬,������������,Ʊ�ݺ���,ժҪ,������,��˽��
			for (int i = 0; i < Integer.parseInt(colums); i++) {
				ws.addCell(new Label(i, 0, dates[i], wcf));
			}

			// �������
			for (int i = 0; i < Integer.parseInt(rows)-1; i++) {
				for(int j =0;j<Integer.parseInt(colums);j++){
					ws.addCell(new Label(j, i + 1, dates[(i+1)*col+j], wcf));
				}			
			}

			wwb.write();
			wwb.close();

		} catch (IOException e) {
		} catch (RowsExceededException e) {
		} catch (WriteException e) {
		}
	}
}
