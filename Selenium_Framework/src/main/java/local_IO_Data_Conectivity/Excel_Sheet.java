package local_IO_Data_Conectivity;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
//import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import org.apache.bcel.generic.RETURN;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.codoid.products.exception.FilloException;
import com.codoid.products.fillo.Connection;
import com.codoid.products.fillo.Fillo;
import com.codoid.products.fillo.Recordset;

public class Excel_Sheet {
 static XSSFWorkbook Wbook;
 static XSSFSheet Sheet;
 XSSFRow Row;
static XSSFCell Cell;
static FileInputStream In;
static FileOutputStream Out;
public static String FilePath;

/*
public static void set_Excel_Sheet(String FilePath1,String Sheet_Name) throws IOException{
	
	In = new FileInputStream(FilePath1);
	Wbook= new XSSFWorkbook(In);
	Sheet = Wbook.getSheet(Sheet_Name);
}
*/

public Excel_Sheet (String FilePath) throws IOException{
	this.FilePath = FilePath;
	this.In= new FileInputStream(FilePath);
	this.Wbook = new XSSFWorkbook(In);
	//this.Out= new FileOutputStream(FilePath);
	In.close();
}
 
//this method is to retrieve the number of rows within a sheet 
	public int retrieveNumberOfRows(String SheetName){
		int index = Wbook.getSheetIndex(SheetName);
		if (index == -1){
			return 0;
		}
		
		Sheet = Wbook.getSheetAt(index);
		int RowNum = Sheet.getLastRowNum();
		return RowNum+1;
	}
	

	//this method is to retrieve the number of column within a sheet 
	
    public int retrieveNumberOfColumns(String SheetName){
		int Index = Wbook.getSheetIndex(SheetName);
		if (Index==-1){
			return 0;
		}
		
		Sheet=Wbook.getSheetAt(Index);
		Row=Sheet.getRow(0);
		int ColuNum = Row.getLastCellNum();
		return ColuNum;
	}
    
	//this method is to retrieve the Run flag for either suite or test cases within the suite 
	public String retriveToRunFlag(String SheetName,String ColName,String RowName){
		int Index = Wbook.getSheetIndex(SheetName);
		if (Index == -1){
			return null;
		}
	
	int Rows = retrieveNumberOfRows(SheetName);
	int Cols= retrieveNumberOfColumns(SheetName);
	int ColIndex = -1;
	int RowIndex= -1;
	
	 Sheet=Wbook.getSheetAt(Index);
	XSSFRow Row1=Sheet.getRow(0);
	
	
	for (int i =0 ; i<Cols ; i++){
			if (Row1.getCell(i).getStringCellValue().equals(ColName.trim())){
				ColIndex=i;			
			}
			
	}
	
	for (int j = 0;j<Rows;j++){
		if (Sheet.getRow(j).getCell(0).getStringCellValue().equals(RowName)){
			RowIndex=j;
		}
	}
	
	return Sheet.getRow(RowIndex).getCell(ColIndex).toString();
	
	}
	
	
	
	
	
	// This Method is to retrieve the test data from the test cases & their data sheet
	public String [] retrievTestData(String SheetName,String ColumnName){
		int Index =Wbook.getSheetIndex(SheetName);
		if (Index ==-1){
			return null;
		}
	
		int Cols= retrieveNumberOfColumns(SheetName);
		int Rows= retrieveNumberOfRows(SheetName);
		int ColLocation =-1;
		
		XSSFRow Row = Wbook.getSheetAt(Index).getRow(0);
		for (int i = 0 ; i<Cols-1;i++){
			if (Row.getCell(i).getStringCellValue().equals(ColumnName)){
				ColLocation = i;
			}
		}
		if (ColLocation == -1){
			return null;
		}
		
		String [] Data  = new String [Rows-1]; 
		for (int j=0;j<Rows-1;j++){
			XSSFRow DataRow = Wbook.getSheetAt(Index).getRow(j+1);
			if (DataRow == null){
				Data[j]="  ";
			}
			
			else{
				XSSFCell DataCell=DataRow.getCell(ColLocation);
				if (DataCell == null){
					Data[j]=" ";
				}
				else{
					Data [j]=DataRow.getCell(ColLocation).toString();
				}
			    }
			}
		return Data;
		}
		
		
	
	/*this method is to return cell data from the excel sheet based on the 
	provided column , row numbers */ 
	public static  Object get_Cell_Data (int col_num,int row_num){
		XSSFCell Cell1 = Sheet.getRow(row_num).getCell(col_num);
		return Cell1;
	}
	
	/*this method is to write data to an excel sheet based on the provided 
	 * column , row numbers*/
	public static void Set_Cell_Data(int col_num,int row_num,String Data , String path) throws IOException{
		Out = new FileOutputStream(path);
		XSSFCell Cell = Sheet.getRow(row_num).getCell(col_num);
		Cell.setCellValue(Data);
		Wbook.write(Out);
		Out.flush();
		Out.close();
	}
	
	/*
	 * this method is to retrieve the number of data rows will be used for a certain test case
	 */
	public  int retrieveTestCaseNumberOfDataRows (String SheetName,String PartialCaseName,int colloc){
		int Index = Wbook.getSheetIndex(SheetName);
		if (Index == -1){
     System.out.println("Wrong Sheet Name");
			return 0;
		}
		else{
			XSSFSheet ReqSheet = Wbook.getSheetAt(Index);
			XSSFRow Row = null;
			XSSFCell Cell = null;
			int ReqRowNum=0;
			int RowNum= retrieveNumberOfRows(SheetName);
			for(int I=1;I<RowNum;I++){
				Row = ReqSheet.getRow(I);
				Cell=Row.getCell(colloc);
				if (Cell.toString().contains(PartialCaseName)){
					ReqRowNum++;
				}	
				
				}
			if (ReqRowNum == 0){
				System.out.println("Wrong Test Case Name");
				return 0;
			}
			
			return ReqRowNum;
		}
		
	}
	
	
	/*
	 * this method is to return the test data row IDs based on the 
	 * execution flag
	 */
	public ArrayList<Integer> Rowlocation(String sheetname,String idcolname,String toruncolname, String toruncolvalue){
		int index = Wbook.getSheetIndex(sheetname);
		if (index ==-1){
			System.out.println("Wrong Sheet name");
			return null;
		}
		else {
			XSSFSheet reqsheet= Wbook.getSheetAt(index);
			int rows = retrieveNumberOfRows(sheetname);
			int cols = retrieveNumberOfColumns(sheetname);
			int idcolloc =-1;
			int toruncolloc =-1;
			
			XSSFRow Row0 = reqsheet.getRow(0);
			
			for (int i =0;i<cols;i++){
				if (Row0.getCell(i).toString().equals(idcolname)){
					idcolloc =i;
				}
				
				if (Row0.getCell(i).toString().equals(toruncolname)){
					toruncolloc =i;
				}
			}
				if (idcolloc==-1){
					System.out.println("Wrong  ID column name");
					return null;
				}
			    if (toruncolloc == -1 ){
						System.out.println("Wrong  torun column name");
						return null;
					}
				ArrayList<Integer> IDs = new ArrayList<Integer>();
				for (int x=1;x<rows;x++){
				XSSFCell flag = reqsheet.getRow(x).getCell(toruncolloc);
				XSSFCell ID = reqsheet.getRow(x).getCell(idcolloc);
				if (flag.toString().equals(toruncolvalue)){
					
				         IDs.add(Integer.parseInt(ID.getRawValue()));
				}
					
				}
				
				
				return IDs;
		}
		
				
		}
		 
	
    
    
	
	
	
	
	/*
	 * this method is to return row numbers of test data related to a certain test case  
	 * based on :
	 *  1-Number of data columns to be retrieved from the data sheet
	 *  2-certain string pattern in the test case name 
	 */
	/*
	public String [][]  retrieveRowNumbers(String SheetName,String TestCaseColName,int DataCols,String pattern){
		int Index = Wbook.getSheetIndex(SheetName);
		if (Index == -1){
			System.out.println("Wrong Sheet Name");
			return null;
	   		}
		    else {
			XSSFSheet Sheet = Wbook.getSheetAt(Index);
			XSSFRow Row= Sheet.getRow(0);
			int testcasecolloc = -1;
			for (int i =0;i<Row.getLastCellNum();i++){
				if (Row.getCell(i).toString().equals(TestCaseColName)){
					testcasecolloc = i;
				}
				if (testcasecolloc ==-1){
					System.out.println("Wrong Test Case Column Name");
					return null;
				}
				else {
					
					int rowcount = retrieveNumberOfRows(SheetName);
					int RowNum = retrieveTestCaseNumberOfDataRows(SheetName,pattern);
					XSSFRow []Rows=new XSSFRow [RowNum];
					for (int j=1;j<rowcount;i++){
                    XSSFRow ReqRows=Sheet.getRow(j);
                    if (ReqRows.getCell(testcasecolloc).toString().contains(pattern)){
                    	
                    }
					}
				}
			}
			
			
			
			
		         }
		
	         }
	
	
	
	
	/*
	 * this method is to retrieve multiple rows of test data to a certain test case
	 */
	public String [][] retreiveTestData(String SheetName){

		int index = Wbook.getSheetIndex(Sheet);
		if (index == -1){
			return null;
		}
		
		XSSFSheet Sheet = Wbook.getSheetAt(index);
		
		int Colnum = retrieveNumberOfColumns(SheetName);
		int Rownum = retrieveNumberOfRows(SheetName);
		String  [][] Data=new String [Rownum-1][Colnum];
		for (int i = 0 ; i<Rownum-1;i++){
			XSSFRow Row = Sheet.getRow(i+1);
			for (int j= 0 ; j<Colnum; j++ ){
				if (Row == null){
					Data [i][j]= " ";
				}
				
				else {
					XSSFCell Cell= Row.getCell(j);
					if (Cell == null){
						Data [i][j] = " ";
					}
				
				else {
					Data [i][j]=Cell.toString();
				}
				
					
				}
				
			
				}
			
		}
		return  Data;

	}
	
	/*
	 * this method is to return test case data from a certain excel sheet 
	 * based on the test data sheet name , Data columns names  
	 * and the value of the (ToRun) flag
	 */
	
	public String [][] retrieveTestCaseData(String SheetName,String DataColName1 , String DataColName2 , String ToRunColumnName , String ToRunColumnValue){
	int index =Wbook.getSheetIndex(SheetName);
	if (index==-1){
		System.out.println("Wrong SheetName");
		return null;
	}
	
	Sheet=Wbook.getSheetAt(index);
	int Rownum=retrieveNumberOfRows(Sheet.getSheetName());
	int Colnum=retrieveNumberOfColumns(Sheet.getSheetName());
	XSSFRow Row= Sheet.getRow(0);
	int DataCol1loc=-1;
	int DataCol2loc=-1;
	int ToRunColloc=-1;
	
	
	for (int i =0;i< Colnum ; i++){
		if (Row.getCell(i).toString().equals(DataColName1)){
			DataCol1loc=i;
		}
		if (Row.getCell(i).toString().equals(DataColName2)){
			DataCol2loc=i;
		}
		
		if (Row.getCell(i).toString().equals(ToRunColumnName)){
			ToRunColloc=i;
		}
	}
	
	//System.out.println("DataCol1loc is : "+DataCol1loc+"     "+"DataCol2loc is : "+DataCol2loc);
	
//	System.out.println("Rownum is : "+Rownum+"     "+"Colnum is : "+Colnum);
	
	if (DataCol1loc== -1 || DataCol2loc == -1 ||ToRunColloc ==-1 ){
		System.out.println("Wrong Column Names");
		return null;
	}
	
	String [][] Data = new String [Rownum-1][2];
	String ToRunFlag = null;
	for (int i =0;i<Rownum-1;i++){
		XSSFRow Row1 = Sheet.getRow(i+1);
		XSSFCell Cell1 = Row1.getCell(ToRunColloc);
		if (Cell1 == null){
			ToRunFlag =" ";
		}
		else
		ToRunFlag = Cell1.getStringCellValue();
		System.out.println("To Run Flag Value Is  "+ToRunFlag);
		for (int j =DataCol1loc;j<DataCol2loc+1;j++){
			//System.out.println("To Run Flag Value Is  "+ToRunFlag);
        if (Row1 == null || !ToRunFlag.equals(ToRunColumnValue) ){
			Data [i][j-1]= " ";
			//Data [i][j-1]= Data[i+1][j-1];
        	//i++;
		}
        else
        {
        	 XSSFCell Cell =Row1.getCell(j);	
        	 if (Cell == null){
        		 Data [i][j-1]= " ";
        	 }
        
        
        else {
        	 Data [i][j-1]=Cell.toString(); 
        }
        	
		
        }
		
	}
		
	}
	return Data;
	}
	
	
	/*
	 * this method is to write test results to test data and test case sheet 
	 */
	public boolean writeTestData(String SheetName,String ColName,int Rownum,String Result){
		int index = Wbook.getSheetIndex(SheetName);
		if (index == -1){
			System.out.println("Wrong Sheet Name");
			return false;		
		}
		else {
			try{
				
			XSSFSheet Sheet = Wbook.getSheetAt(index);
			XSSFRow Row = Sheet.getRow(0);
			int Cols = retrieveNumberOfColumns(SheetName);
			int Rows = retrieveNumberOfRows(SheetName);
			int Colloc =-1;
			for (int i=0 ; i<Cols;i++){
				if (Row.getCell(i).toString().equals(ColName.trim()))
				Colloc = i;
			}
			
			System.out.println("getting cells");
			
			if (Colloc == -1){
				System.out.println("Wrong Col Name");
				return false;
			}
			
			XSSFRow ReqRow = Sheet.getRow(Rownum);
			XSSFCell ReqCell = ReqRow.getCell(Colloc);
			if (ReqCell == null){
				ReqCell = ReqRow.createCell(Colloc);
			}
				ReqCell.setCellValue(Result);
				
				System.out.println("writing data");
				Wbook.write(Out);
				Out.flush();
				Out.close();
			
			
			
			}
			catch (Exception E){
				E.printStackTrace();
				return false;
			}
			return true;
		     }
	}
	
	
	/*
	 * this method is to write test results to test suites sheet 
	 */
	
	public boolean writeResults(String SheetName1,String ColName,String RowName ,String Result) throws IOException{
    int index = Wbook.getSheetIndex(SheetName1);
if (index == -1){
System.out.println("Wrong Sheet Name");
return false;
}
else{
	XSSFSheet Sheet = Wbook.getSheetAt(index);
	XSSFRow Row = Sheet.getRow(0);
	int ColNum= retrieveNumberOfColumns(SheetName1);
	int RowNum = retrieveNumberOfRows(SheetName1);
	
	int ReqCol=-1;
	int ReqRow=-1;
	System.out.println("entering the loop");
	
	for (int i = 0;i<ColNum;i++){
		if (Row.getCell(i).toString().equals(ColName)){
			ReqCol=i;
			System.out.println("got the col number");
		}

		System.out.println("out of comaprison in  the loop");
	}
	if (ReqCol ==-1){
		System.out.println("Wrong Column Name");
		return false;
	}
	for (int j = 1;j<RowNum;j++){
		XSSFRow Row1=Sheet.getRow(j);
		if (Row1.getCell(0).toString().equals(RowName)){
			ReqRow=j;
		}
		System.out.println("got the row number");
	}
	if (ReqRow ==-1){
		System.out.println("Wrong Test Suite  Name");
		return false;
	}
	
	XSSFCell ReqCell= Sheet.getRow(ReqRow).getCell(ReqCol);
			ReqCell.setCellValue(Result);
			
	FileOutputStream out1=new FileOutputStream(FilePath);
     Wbook.write(out1);
     out1.flush();
     out1.close();

	return true;
	
}
	





	}
	
	
	
	/*
	public static void DB() throws ClassNotFoundException, SQLException{
		Class.forName("oracle.jdbc.driver.OracleDriver"); 
		Connection c = java.sql.DriverManager.getConnection( "jdbc:odbc:Driver={Microsoft Excel Driver (*.xls)};DBQ=C:\\Data_Test\\framework\\FrameWork.xls");
		String Qeery="select * from Divide";
		Statement Stmt = c.createStatement();
		ResultSet RS = Stmt.executeQuery(Qeery);
		while(RS.next()){
			RS.getString(0);
		}
		
	}*/
	
	public  String[][] fillo() throws FilloException{
		Fillo fillo=new Fillo();
		Connection connection=fillo.getConnection("C:\\Data_Test\\framework\\FrameWork.xlsx");
		ArrayList<Integer> Ids= Rowlocation("Divide","ID","CaseToRun","y");
		String [][] Data = new String [Ids.size()][2];
		for (int i=0;i<Ids.size();i++){
			int y=Ids.get(i);
			String Query ="Select Data1Column , Data2Column from Divide where ID="+y;
			Recordset recordset=connection.executeQuery(Query);	
			while (recordset.next()){
		for (int x=0;x<2;x++){
			Data[i][x]=recordset.getField(x).value();
			
		}
			}
		//recordset.close();
		//connection.close();
		}
		//recordset.close();
		return Data;
		
		//recordset.close();
		
	}
	

	
	
	
	
	// this method is to test the written code
	public static void main(String[]args) throws IOException, ClassNotFoundException, SQLException, FilloException{
/*set_Excel_Sheet("C:\\Data_Test\\Excel_test.xlsx", "Sheet1");
Set_Cell_Data(1,1,"Data Test120","C:\\Data_Test\\Excel_test.xlsx");
String Data = get_Cell_Data(1,1).toString();
System.out.println(Data);
*/
		Excel_Sheet Sheet = new Excel_Sheet("C:\\Data_Test\\framework\\FrameWork.xlsx");
		//int Rows = Sheet.retrieveNumberOfRows("Divide");
		//int Cols =Sheet.retrieveNumberOfColumns("Divide");
		//int ReqRows=Sheet.retrieveTestCaseNumberOfDataRows("Sheet1","mohamed");
		//String Data = Sheet.retriveToRunFlag("Sheet1", "hamada", "9");
		//String []TestData= Sheet.retrievTestData("Sheet1", "hamada");
		//String [][]AllTestData = Sheet.retreiveTestData("Divide");
		//Sheet.writeTestData("Sheet1","Letter",6, "writing data");
		//Sheet.writeResults("Sheet1", "Letter", "Emp7", "test suite data");
		//System.out.println(Rows+" , "+Cols);
		//System.out.println(Arrays.toString(TestData));
	  //System.out.println(Arrays.deepToString(AllTestData));
		//System.out.println(ReqRows);
	 //  String Data [][] =Sheet.retrieveTestCaseData("Divide","Data1Column","Data2Column","CaseToRun","y");
	   // System.out.println(Arrays.deepToString(Data));
		//DB();
	  //fillo();
		/*ArrayList<Integer> Ids= Sheet.Rowlocation("Divide","ID","CaseToRun","y");
		for (int i=0;i<Ids.size();i++){
	  System.out.println(Ids.get(i));
		}*/
		String[][] Data=Sheet.fillo();
		System.out.println(Arrays.deepToString(Data));
	}
}

