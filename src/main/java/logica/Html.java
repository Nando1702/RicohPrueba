package logica;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import model.Registro;

public class Html {

	private ArrayList<Registro> registros;

	private Map<String, Map<String, Integer>> centrosMeses;

	private Map<String, Map<String, Integer>> centrosDias;

	private Map<String, Integer> ocurrenciasPersonales;

	private Map<String, Integer> ocurrenciasCentros;

	private static final String KEY_NUM = "numeroRegistros";

	public Html(ArrayList<Registro> registros) {
		super();
		this.registros = registros;
		this.centrosDias = new HashMap<String, Map<String, Integer>>();
		this.centrosMeses = new HashMap<String, Map<String, Integer>>();
		this.ocurrenciasPersonales = new HashMap<String, Integer>();
		this.ocurrenciasCentros = new HashMap<String, Integer>();
		contarOcurrencias();
		construirHtml();
	}

	private void contarOcurrencias() {

		for (Registro registro : registros) {
			
			// LAS LLAVES VAN A SER LOS CENTROS, SI EL CENTRO NO EXISTE EN EL MAP LO CREA

			Map<String, Integer> aux = centrosMeses.computeIfAbsent(registro.getWorkcenter(), k -> new HashMap<>());

			Map<String, Integer> aux2 = centrosDias.computeIfAbsent(registro.getWorkcenter(), k -> new HashMap<>());
			
			// LA LLAVE SERA LOS EMAILS, CADA VEZ QUE ENCUENTRE UNO REPETIDO COGERA 
			// EL VALOR ANTIGUO Y LE AÑADE UNO POR SU DEFECTO SE INICIALIZARA A 0

			ocurrenciasPersonales.put(registro.getEmail(),
					ocurrenciasPersonales.getOrDefault(registro.getEmail(), 0) + 1);
			
			
			
			ocurrenciasCentros.put(KEY_NUM, aux.getOrDefault(KEY_NUM, 0) + 1);

			// LA LLAVE ES EL MES, HAZE LO MISMO QUE EL DE LOS EMAILS
			
			aux.put(obtenerMes(registro.getBookingDate()),
					aux.getOrDefault(obtenerMes(registro.getBookingDate()), 0) + 1);
			
			// LA LLAVE ES LA FECHA

			aux2.put(registro.getBookingDate().toString(),
					aux2.getOrDefault(registro.getBookingDate().toString(), 0) + 1);
						
		}

	}

	private String obtenerMes(Date date) {
		
		// CON ESTO OBTENGO EL NOMBRE DEL MES

		SimpleDateFormat formatoMes = new SimpleDateFormat("MMMM");
		return formatoMes.format(date);

	}

	private void construirHtml() {
		
		// PARA HACER EL HTML, ESCRIBO UN FICHERO CON EXTENSION HTML

		try (BufferedWriter writer = new BufferedWriter(new FileWriter("registros.html"))) {

			writer.write("<html>");
			writer.newLine();
			writer.write("<head>");
			writer.newLine();
			writer.write("<title>Registro Reservas</title>");

			// CSS
			
			writer.write("<style>");
			writer.newLine();
			writer.write("body { text-align: center; font-family: Arial, sans-serif; }");
			writer.newLine();
			writer.write(".bg-light { background-color: #f8f9fa; }");
			writer.newLine();
			writer.write(".rounded { border-radius: 0.2rem; }");
			writer.newLine();
			writer.write(".p-3 { padding: 1rem; }");
			writer.newLine();
			writer.write(".pb-md-4 { padding-bottom: 2rem; }");
			writer.newLine();
			writer.write(".mx-auto { margin-left: auto; margin-right: auto; }");
			writer.newLine();
			writer.write(".text-center { text-align: center; }");
			writer.newLine();
			writer.write("h1.display-4 { font-size: 2.5rem; }");
			writer.newLine();
			writer.write("h1.fw-normal { font-weight: normal; }");
			writer.newLine();
			writer.write("h2 { font-size: 1rem; color: #808080; font-weight: normal; }");
			writer.newLine();
			writer.write(
					".left-title { text-align: left; font-size: 1.2rem; font-weight: bold; margin-bottom: 10px; }");
			writer.newLine();
			writer.write(
					"table { width: 80%; margin-left: auto; margin-right: auto; border-collapse: collapse; margin-top: 20px; }");
			writer.newLine();
			writer.write("th, td { border: 1px solid #dddddd; text-align: left; padding: 8px; }");
			writer.newLine();
			writer.write("th { th { background-color: #f2f2f2; border-bottom: 2px solid black; } }");
			writer.newLine();
			writer.write("</style>");
			writer.newLine();
			writer.write("</head>");
			writer.newLine();
			writer.write("<body>");

			// TITULO
			
			writer.write("<div class=\"bg-light rounded p-3 pb-md-4 mx-auto text-center\">");
			writer.newLine();
			writer.write("<h1 class=\"display-4 fw-normal\">Registros de Reserva</h1>");
			writer.newLine();
			
			// SUBTITULO
			writer.write("<h2>Resumen de Reservas Realizadas</h2>");
			writer.newLine();
			writer.write("</div>");

			// MARGEN GRIS
			writer.write("<div class=\"left-title\">Agrupado por: Mes / Centro</div>");

			// PRIIMERA TABLA
			
			writer.write("<table>");
			writer.newLine();
			writer.write("<tr>");
			writer.write("<th>Mes</th>");
			writer.write("<th>Centro</th>");
			writer.write("<th>Num. Reservas</th>");
			writer.write("</tr>");
			
			// RECOREMOS EL MAP PARA PODER REPRESENTAR LOS VALORES

			for (Map.Entry<String, Map<String, Integer>> entry : centrosMeses.entrySet()) {

				String centro = entry.getKey();
				Map<String, Integer> aux = entry.getValue();
				
				// RECOREMOS LOS MAPS INTERIOR

				for (Map.Entry<String, Integer> entry2 : aux.entrySet()) {

					String key = entry2.getKey();
					Integer val = entry2.getValue();

					writer.write("<tr>");
					writer.write("<td>" + key + "</td>");
					writer.write("<td>" + centro + "</td>");
					writer.write("<td>" + val + "</td>");
					writer.write("</tr>");

				}

			}
			
			writer.write("</table>");
			writer.newLine();
			
			// SEGUNDA TABLA
			
			// ETIQUETA, ENCABEZADO DE TABLA
			
			writer.write("<div class=\"left-title\">Agrupado por: Dia / Centro</div>");

			// DEFINICION TABLA
			
			writer.write("<table>");
			writer.newLine();
			writer.write("<tr>");
			writer.write("<th>Dia</th>");
			writer.write("<th>Centro</th>");
			writer.write("<th>Num. Reservas</th>");
			writer.write("</tr>");
			
			// RECOREMOS MAP

			for (Map.Entry<String, Map<String, Integer>> entry : centrosDias.entrySet()) {

				String centro = entry.getKey();
				Map<String, Integer> aux = entry.getValue();

				for (Map.Entry<String, Integer> entry2 : aux.entrySet()) {

					String key = entry2.getKey();
					Integer val = entry2.getValue();

					writer.write("<tr>");
					writer.write("<td>" + key + "</td>");
					writer.write("<td>" + centro + "</td>");
					writer.write("<td>" + val + "</td>");
					writer.write("</tr>");

				}

			}

			writer.write("</table>");
			writer.newLine();
			
			// 3A TABLA 
			
			// ETIQUETA

			writer.write("<div class=\"left-title\">Ranking asistencia / Centro</div>");

			writer.write("<table>");
			writer.newLine();
			writer.write("<tr>");
			writer.write("<th>Puesto</th>");
			writer.write("<th>Email</th>");
			writer.write("<th>Num. Reservas</th>");
			writer.write("</tr>");
			
			// ORDENO EL MAP DONDE ESTAN LOS REGISTROS POR PERSONA/EMAIL
			// CREA UN CONTANDOR PARA PONER LOS PUESTOS Y DELIMITAR LA LISTA A 10
			
			ocurrenciasPersonales = ordenarMap();
			int cont = 1;
			
			// SE RECORRE EL MAP UNA VEZ YA ORDENADO
			
			for (Map.Entry<String, Integer> entry : ocurrenciasPersonales.entrySet()) {
				
				if (cont == 11) {
					
					break;
				}
				
				String key = entry.getKey();
				Integer val = entry.getValue();
				
				writer.write("<tr>");
				writer.write("<td>" + cont + "</td>");
				writer.write("<td>" + key + "</td>");
				writer.write("<td>" + val + "</td>");
				writer.write("</tr>");
				
				cont++;
				
			}

			// CIERRE DEL BODY Y HTML
			writer.write("</body>");
			writer.newLine();
			writer.write("</html>");

		} catch (Exception e) {

			System.out.println(e.getMessage());

		}

	}

	private Map<String, Integer> ordenarMap() {

		List<Map.Entry<String, Integer>> listaEntradas = new ArrayList<>(ocurrenciasPersonales.entrySet());

		// ORDENO LA LISTA CON UN COMPARADOR
		listaEntradas.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));

		// CREA EL NUEVO MAP
		Map<String, Integer> mapaOrdenado = listaEntradas.stream().collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));

		return mapaOrdenado;
		
	}

}
