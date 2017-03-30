package inputFile;

import org.apache.poi.xssf.usermodel.XSSFRow;

//import utility.preConfiguration;

public class ResultUpdate {
	public static String sPath=null;
	public static String sFileName=null;
	private static String sReportPath=null;
	private static String sSheetName="Details";

	public static void setPathForResultUpdate() throws Exception {
		sReportPath=sPath+sFileName;

		ExcelUtility.setExcelFile(sReportPath, sSheetName);
		return;
	}

	public static void setPathForResultUpdate(String SheetName, String sTestStartDate, String sTimeZone, String sBrowserInfo, String sOSInfo) {
		sReportPath=sPath+sFileName;
		try {
			ExcelUtility.setExcelFile(sReportPath, SheetName);
			int iRowNo=2, iColNo=6;
			ExcelUtility.setStaticCellData(sTestStartDate, iRowNo, iColNo, sReportPath);

			iRowNo=iRowNo+1;
			ExcelUtility.setStaticCellData(sTimeZone, iRowNo, iColNo, sReportPath);

			iRowNo=iRowNo+1;
			ExcelUtility.setStaticCellData(sBrowserInfo, iRowNo, iColNo, sReportPath);

			iRowNo=iRowNo+1;
			ExcelUtility.setStaticCellData(sOSInfo, iRowNo, iColNo, sReportPath);			
		} catch (Exception e) {
			e.printStackTrace();
		}


		return;

	}// End of setPathForResultUpdate



	public static void updateResult(String sURL, String sSection, boolean bResult, String sComments, int iPriority) {
		String sResult,slNumber,sPriority = null;
		try {

			int iRowNo = ExcelUtility.getRowCount()+1;

			XSSFRow newRow = ExcelUtility.getRow(iRowNo);

			int iSlNumber=0;
			slNumber=ExcelUtility.getCellData(iRowNo-1, 0);

			if(slNumber.equalsIgnoreCase("Sl No")){
				iSlNumber=iSlNumber+1;
			}
			else {
				iSlNumber=Integer.parseInt(slNumber);
				iSlNumber=iSlNumber+1;
			}
			slNumber=Integer.toString(iSlNumber);


			int iColNo=0;

			ExcelUtility.setCellData(slNumber, newRow,iColNo, sReportPath);

			iColNo=iColNo+1;
			ExcelUtility.setStaticCellData(sURL, iRowNo, iColNo, sReportPath);

			iColNo=iColNo+1;
			ExcelUtility.setStaticCellData(sSection, iRowNo, iColNo, sReportPath);

			if(bResult==true){
				sResult="Pass";
				sPriority="Pass";
			}
			else {
				sResult="FAIL";
			}

			iColNo=iColNo+1;
			ExcelUtility.setStaticCellData(sResult, iRowNo, iColNo, sReportPath);

			iColNo=iColNo+1;
			ExcelUtility.setStaticCellData(sComments, iRowNo, iColNo, sReportPath);

			if(!sPriority.equalsIgnoreCase("Pass")){
				iColNo=iColNo+1;
				ExcelUtility.setStaticCellData(sPriority, iRowNo, iColNo, sReportPath);
			}
		}
		catch ( Exception e){
			e.printStackTrace();
		}
	}
}
