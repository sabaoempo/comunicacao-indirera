
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class Planilha {
	  public static void main(String[] argv) throws Exception {
		  lerLivroOferta("C:/Users/karol/OneDrive/Documentos/DAMD/Ex11_RabbitMQ_PubSub/LivroDeOfertas.csv");
		  
	  }
		  public static ArrayList run(String arquivoCSV) {
			 ArrayList<String> cod = new ArrayList<String>();
		    BufferedReader br = null;
		    String linha = "";
		    String csvDivisor = ";";
		    try {
		    	
		        br = new BufferedReader(new FileReader(arquivoCSV));
		        for (int i=0;(linha = br.readLine()) != null; i++) {
		        	if(i!=0) {
		            String[] codigo = linha.split(csvDivisor);
		            cod.add(codigo[0]);
		        	}
		        }
		        return cod;

		    } catch (FileNotFoundException e) {
		        e.printStackTrace();
		    } catch (IOException e) {
		        e.printStackTrace();
		    } finally {
		    	
		        if (br != null) {
		            try {
		                br.close();
		            } catch (IOException e) {
		                e.printStackTrace();
		            }
		        }
		    }
			return null;
		  }
		  
		  public static ArrayList lerLivroOferta(String arquivoCSV) {
				ArrayList<String> cod = new ArrayList<String>();
			    BufferedReader br = null;
			    String linha = "";
			    String csvDivisor = ",";
			    try {
			    	
			        br = new BufferedReader(new FileReader(arquivoCSV));
			        for (int i=0;(linha = br.readLine()) != null; i++) {
			            String[] codigo = linha.split(csvDivisor);
			            cod.add(linha);
			        }
			        return cod;

			    } catch (FileNotFoundException e) {
			        e.printStackTrace();
			    } catch (IOException e) {
			        e.printStackTrace();
			    } finally {
			    	
			        if (br != null) {
			            try {
			                br.close();
			            } catch (IOException e) {
			                e.printStackTrace();
			            }
			        }
			    }
				return null;
			  }
}
