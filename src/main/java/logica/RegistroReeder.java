package logica;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

import model.Registro;

public class RegistroReeder {

	private final String RUTA;

	private ArrayList<Registro> registros;

	public RegistroReeder(String rUTA) {
		super();
		this.RUTA = rUTA;
		registros = new ArrayList<Registro>();
		getAllRegistro();
	}
	
	// METODO PARA OBTENER UNA LISTA DE TODOS LOS REGISTROS

	private ArrayList<Registro> getAllRegistro() {

		File file = new File(RUTA);

		try (FileInputStream fis = new FileInputStream(file)) {

			Workbook wb = new XSSFWorkbook(fis);
			DataFormatter dataFormatter = new DataFormatter();
			DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy, HH:mm:ss");
			
			for (int sheetIndex = 0; sheetIndex < wb.getNumberOfSheets(); sheetIndex++) {
				Sheet sheet = wb.getSheetAt(sheetIndex);
				boolean encabezado = true; 
					
				// LA PRIMERA FILA, LA SALTO 
				
				for (Row row : sheet) {
					
					if (encabezado) {
						encabezado = false;
						continue;
						
					}
					
					
					Date bookingDate = row.getCell(0).getDateCellValue();
					System.out.println(bookingDate);
                    String email = row.getCell(1, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL).getStringCellValue();
                    System.out.println(email);
                    String activate = row.getCell(2, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL).getStringCellValue();
                    boolean activated = Boolean.parseBoolean(activate);
                    Date activationDate = parseDate(dataFormatter.formatCellValue(row.getCell(3)), dateTimeFormatter);
                    Date entryDate = parseDate(dataFormatter.formatCellValue(row.getCell(4)), dateTimeFormatter);
                    String workcenter = row.getCell(5, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL).getStringCellValue();
                    String workshift = row.getCell(6, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL).getStringCellValue();

				    registros.add(new Registro(bookingDate, email, activated, activationDate, entryDate, workcenter, workshift));
				    
				    
					
					
				}
			}

		} catch (Exception e) {
			
			System.out.println(e.getMessage());

		}

		return null;
	}
	

	public ArrayList<Registro> getRegistros() {
		return registros;
	}
	
	// ME DABA UN ERROR POR EL FORMATO DE LA FECHA
	
	private Date parseDate(String dateString, DateTimeFormatter dateTimeFormatter) {
	    try {
	    
	        LocalDateTime localDateTime = LocalDateTime.parse(dateString, dateTimeFormatter);
	        return java.sql.Timestamp.valueOf(localDateTime);
	    
	    } catch (Exception e) {
	        
	    	System.err.println(e.getMessage());
	        return null;
	    }
	}

}
