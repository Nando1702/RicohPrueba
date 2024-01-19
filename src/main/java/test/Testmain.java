package test;

import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

import logica.Html;
import logica.RegistroReeder;
import model.Registro;

public class Testmain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		RegistroReeder rr = new RegistroReeder("input.xlsx");
		
		ArrayList<Registro> r = rr.getRegistros();
		
		Html html = new Html(r);
		
		

	}

}
