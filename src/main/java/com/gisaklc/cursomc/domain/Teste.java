package com.gisaklc.cursomc.domain;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Teste {

	private static Set<String> telefones = new HashSet<String>();

	public static void main(String[] args) throws IOException, InterruptedException {

//		Retangulo r = new Retangulo();
//		double p = r.getPerimetro();
//		double a = r.getArea();
//
//		System.out.println("Area: " + a);
//		System.out.println("Perimeter: " + p);
		//calcularTotal();
		
		
	     //  Scanner scanner = new Scanner(System.in);

	      //  System.out.print("Digite o cep de pesquisa: ");
	      //  String cep = scanner.nextLine();

	     //   getCep("");
		
			//int[][] matriz = {{1, 2, 3, 4, 5, 6}, {8, 10, 12, 14}};
			//Arrays.asList(matriz).forEach((i) -> { System.out.println(i.toString()); });
			
			String[][] arr = new String[][] { { "Molho de Tomate", "1.70" }, { "Filet Mignon", "39.90" },
				{ "Queijo Mussarela", "9.90" }, { "Farinha de Rosca", "4.90" }, { "Caixa de Ovos", "7.90" } };

			
				String[][] str2DArray = new String[][]{ {"John", "Bravo"} , {"Mary", "Lee"}, {"Bob", "Johnson"} };

			    //Prior to Java 8
			  //  System.out.println(Arrays.deepToString(arr));
			    Arrays.stream(arr).flatMap(x -> Arrays.stream(x)).forEach(System.out::println);
			
	}

	private static double calcularTotal() {
		
		String[][] arr = new String[][] { { "Molho de Tomate", "1.70" }, { "Filet Mignon", "39.90" },
				{ "Queijo Mussarela", "9.90" }, { "Farinha de Rosca", "4.90" }, { "Caixa de Ovos", "7.90" } };
		double total = 0;

		for (String[] i : arr) {
			int index = 0;
			for (String j : i) {
				if (index == 1) {
					double valor = Double.valueOf(j);
					total += valor;
				}
				index++;
			}
		}
		System.out.print(total);
		
		
//		for (int col = 0; col < arr[0].length; col++) {
//			for (int row = 0; row < arr.length; row++) {
//				if (col == 1) {
//					double valor = Double.valueOf(arr[row][col]);
//					total += valor;
//				} else {
//					break;
//				}
//			}
//
//		}

		return total;

	}

	private void buscarCidade() {

		Map<String, Integer> cidades = new HashMap<String, Integer>();

		cidades.put("São Paulo", 123363521);
		cidades.put("Campinas", 2615322);
		cidades.put("Belo Horizonte", 2722369);
		cidades.put("Rio de Janeiro", 6748321);
		cidades.put("Salvador", 2886698);

		for (String key : cidades.keySet()) {
			if (key.equals("Salvador")) {
				Integer value = cidades.get(key);
				System.out.println(key + " = " + value);
			}
		}
	}
	
	private static void getCep(String cep) throws IOException, InterruptedException {
//        HttpClient client = HttpClient.newHttpClient();
//
//        HttpRequest request = HttpRequest.newBuilder()
//                .uri(URI.create("https://viacep.com.br/ws/" + cep + "/json/"))
//                .build();
//
//        HttpResponse<String> response = client.send(request,
//                HttpResponse.BodyHandlers.ofString());
//
//        System.out.println(response.body());
        
        
        
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
              .uri(URI.create("https://uniciv.myedools.com/" + "/json/"))
              .build();
        client.sendAsync(request, BodyHandlers.ofString())
              .thenApply(HttpResponse::body)
              .thenAccept(System.out::println)
              .join();
        

        
    }

}
