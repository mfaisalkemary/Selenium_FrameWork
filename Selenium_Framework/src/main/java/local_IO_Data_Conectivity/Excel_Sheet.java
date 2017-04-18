package local_IO_Data_Conectivity;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import org.apache.bcel.generic.RETURN;
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

public static void set_Excel_Sheet(String FilePath,String Sheet_Name) throws IOException{
	In = new FileInputStream(FilePath);
	Wbook= new XSSFWorkbook(In);
	Sheet = Wbook.getSheet(Sheet_Name);
}
 

	
	/*this method is to return cell data from the excel sheet based on the 
	provided column , row numbers */ 
	public static  Object get_Cell_Data (int col_num,int row_num){
		XSSFCell Cell1 = Sheet.getRow(row_num).getCell(col_num);
		return Cell1;
	}
	
	/*this method is to write data to an excel sheet based on the provided 
	 * column , row numbers*/
	public static void Set_Cell_Data(int col_num,int row_num,String Data) throws IOException{
		XSSFCell Cell = Sheet.getRow(row_num).getCell(col_num);
		Cell.setCellValue(Data);
		Wbook.write(Out);
		Out.flush();
		Out.close();
		
	}
	
	/*this method is to return a row from the excel sheet based on the 
	 * value of a cell in a certain column in the sheet (a flag column to use 
	 * this set of data or not to use) 
	
	public  String[] Control(int row_num,int col_num,boolean control){
		
	}
	*/
	
	// this method is to test the written code
	public static void main(String[]args) throws IOException{
		set_Excel_Sheet("C:\\Data_Test\\Excel_test_2.xlsx", "Sheet1");
		Set_Cell_Data(1,1,"Data Test");
		String Data = get_Cell_Data(1,1).toString();
		System.out.println(Data);
		
	}
}

