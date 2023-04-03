package application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;
import entities.Sale;

public class Program
{
	// C:\EclipseProjetos\desafio_analise_vendas_02\in.csv
	
	/*
	 * Quero deixar aqui meu agradecimento a professora Luciene Oliveira
	 * que indicou o código de uso do map para a solução do desafio. Eu
	 * estava usando o Set e me perdi sobre qual estrura usar depois para
	 * filtrar e exibir o resultado. Muito obrigado, professora!
	 */
	
	public static void main(String[] args)
	{
		Locale.setDefault(Locale.US);
		Scanner scn = new Scanner(System.in);
		
		System.out.print("Entre o caminho do arquivo: ");
		String caminho = scn.nextLine();
		
		try (BufferedReader bfr = new BufferedReader(new FileReader(caminho)))
		{			
			List<Sale> lista = new ArrayList<>();			
			
			String linha = bfr.readLine();
			
			while (linha != null)
			{
				String[] campos = linha.split(",");
				lista.add(new Sale(
					Integer.parseInt(campos[0]),     // mês
					Integer.parseInt(campos[1]),     // ano
					campos[2],                       // vendedor
					Integer.parseInt(campos[3]),     // itens
					Double.parseDouble(campos[4]))); // total
				linha = bfr.readLine();									
			}
			
			System.out.println();
			
			Map<String, Double> mapa = new HashMap<>();
			
			for (Sale venda : lista)
			{
				mapa.put(venda.getSeller(), 0.0);
			}
			
			for (String vendedor : mapa.keySet())
			{
				Double total = lista.stream()
					.filter(s -> s.getSeller().equals(vendedor))
					.map(s -> s.getTotal())
					.reduce(0.0, (x,y) -> x + y);
				mapa.put(vendedor, total);
			}
			
			System.out.println("Total de vendas por vendedor:");
			
			for (String vendedor : mapa.keySet())
			{
				System.out.printf(vendedor + " - R$ %.2f \n", mapa.get(vendedor));
			}									
		}
		catch (IOException e)
		{
			System.out.println("Erro: " + caminho + " (O sistema não pode encontrar o arquivo especificado)");
		}
		finally
		{
			scn.close();
		}
	}
}
