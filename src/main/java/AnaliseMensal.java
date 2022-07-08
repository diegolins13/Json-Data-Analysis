import java.io.FileReader;

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
	
		try(FileReader reader = new FileReader("C:\\temp\\ws-eclipse\\desafioTarget\\dados.json")){
			
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
		
		System.out.println("Menor valor de faturamento: " + menorValor);
		System.out.println("Maior valor de faturamento: " + maiorValor);
		System.out.println("Dias superior a media: " + countDiasSup);
	}
}
