package local_IO_Data_Conectivity;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import org.apache.bcel.generic.RETURN;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

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
		
		
	
	
	
  // this method is to get the data row of a certain test case which is having a to-run flag = 1 
	
    
    
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
	 * this method is to retrieve the test data for all the test cases 
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
	
	
	
	
	
	
	
	
	// this method is to test the written code
	public static void main(String[]args) throws IOException{
/*set_Excel_Sheet("C:\\Data_Test\\Excel_test.xlsx", "Sheet1");
Set_Cell_Data(1,1,"Data Test120","C:\\Data_Test\\Excel_test.xlsx");
String Data = get_Cell_Data(1,1).toString();
System.out.println(Data);
*/
		Excel_Sheet Sheet = new Excel_Sheet("C:\\Data_Test\\Excel_test.xlsx");
		int Rows = Sheet.retrieveNumberOfRows("Sheet1");
		int Cols =Sheet.retrieveNumberOfColumns("Sheet1");
		//String Data = Sheet.retriveToRunFlag("Sheet1", "hamada", "9");
		//String []TestData= Sheet.retrievTestData("Sheet1", "hamada");
		//String [][]AllTestData = Sheet.retreiveTestData("Sheet1");
		//Sheet.writeTestData("Sheet1","Letter",6, "writing data");
		Sheet.writeResults("Sheet1", "Letter", "Emp7", "test suite data");
		System.out.println(Rows+" , "+Cols);
		//System.out.println(Arrays.toString(TestData));
	//	System.out.println(Arrays.deepToString(AllTestData));
		
	}
}

