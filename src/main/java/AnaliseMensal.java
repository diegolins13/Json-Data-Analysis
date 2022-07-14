import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class AnaliseMensal {
	
	private static Double faturamentoDia(JSONObject dados) {
		
		return Double.parseDouble(dados.get("valor").toString());
	}

	public static void main(String[] args) throws Exception {
		
		JSONParser jsonParser = new JSONParser();
		
		double menorValor = 0;
		double maiorValor = 0;	
		double mediaMensal = 0;
		double soma = 0;
		int countDiasUteis = 0;
		int countDiasSup = 0;
	
		try(FileReader reader = new FileReader("C:\\temp\\ws-eclipse\\analiseDados-maven\\dados.json")){
			
			Object obj = jsonParser.parse(reader);
			JSONArray listaMensal = (JSONArray) obj;
			
			//For de maior valor
			for(int i=0; i < listaMensal.size(); i++ ) {
				if(faturamentoDia((JSONObject) listaMensal.get(i)) > 0 && faturamentoDia((JSONObject) listaMensal.get(i)) > maiorValor){
					maiorValor = faturamentoDia((JSONObject) listaMensal.get(i));
				}
				if(faturamentoDia((JSONObject) listaMensal.get(i)) > 0) {
					countDiasUteis += 1;
				}
				soma += faturamentoDia((JSONObject) listaMensal.get(i));
			}
			menorValor = faturamentoDia((JSONObject) listaMensal.get(0));
			for(int i=0; i < listaMensal.size(); i++ ) {
				if(faturamentoDia((JSONObject) listaMensal.get(i)) > 0.0 && faturamentoDia((JSONObject) listaMensal.get(i)) < menorValor){
					menorValor = faturamentoDia((JSONObject) listaMensal.get(i));
				}
			}
			mediaMensal = soma / countDiasUteis;
			for(int i=0; i < listaMensal.size(); i++ ) {
				if(faturamentoDia((JSONObject) listaMensal.get(i)) > mediaMensal) {
					countDiasSup += 1;
				}
			}
		}catch(Exception e) {
			
		}	
		FileWriter arq = new FileWriter("C:\\temp\\ws-eclipse\\analiseDados-maven\\relatorio.txt");
		PrintWriter gravarArq = new PrintWriter(arq);
		
	    gravarArq.printf("Análise Financeira\n");
	    gravarArq.printf("------------------\n");
	    
	    gravarArq.println("Dias de faturamento: " + countDiasUteis);
	    gravarArq.printf("Média mensal: R$ %.2f%n", mediaMensal);
	    gravarArq.printf("Menor valor de faturamento: R$ %.2f%n", menorValor);
	    gravarArq.printf("Maior valor de faturamento: R$ %.2f%n", maiorValor);
	    gravarArq.println("Dias superior a media: " + countDiasSup);
	    arq.close();
	}
}
