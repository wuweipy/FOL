package View.Export;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

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
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

public class Export extends HttpServlet {

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

	/**
	 * The doGet method of the servlet. <br>
	 * 
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	@Override
	protected void service(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String ms_code = "11";

		/* 生成表单开始 */
		exportExcel(downloadPath+ms_code+".xls",ms_code);
		/* 生成表单结束 */
		ms_code = new String(ms_code.getBytes("ISO-8859-1"));
		File source = new File(downloadPath + ms_code + ".xls");
		response.reset();
		response.setContentType("application/force-download");
		response.setHeader("Content-Disposition", "attachment; filename=\""
				+ source.getName() + "\"");
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		ServletOutputStream sos = response.getOutputStream();
		BufferedInputStream fin = new BufferedInputStream(new FileInputStream(
				source));
		byte[] content = new byte[1024];
		int length;
		while ((length = fin.read(content, 0, content.length)) != -1) {
			sos.write(content, 0, length);
		}
		fin.close();
		sos.flush();
		sos.close();
	}

	public static void exportExcel(String fileName, String ms_code) {
		WritableWorkbook wwb;
		FileOutputStream fos;
		try {
			fos = new FileOutputStream(fileName);
			wwb = Workbook.createWorkbook(fos);

			// 创建一个工作表
			WritableSheet ws = wwb.createSheet(ms_code, 10);
			// 设置单元格的文字格式
			WritableFont wf = new WritableFont(WritableFont.ARIAL, 12,
					WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE,
					Colour.BLUE);
			WritableCellFormat wcf = new WritableCellFormat(wf);
			wcf.setVerticalAlignment(VerticalAlignment.CENTRE);
			wcf.setAlignment(Alignment.CENTRE);
			ws.setRowView(1, 500);
			
			for(int i =1;i<100;i++){
				ws.addCell(new jxl.write.Number(0,i,i+1000));
			}

			wwb.write();
			wwb.close();

		} catch (IOException e) {
		} catch (RowsExceededException e) {
		} catch (WriteException e) {
		}
	}

}
