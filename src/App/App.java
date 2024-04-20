package App;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import model.entitites.Product;

public class App {
	public static void main(String[] args) {
		String sourceFile = "C:\\Users\\User\\eclipse-workspace\\Average_prices\\src\\repository\\products.csv";

		try (BufferedReader br = new BufferedReader(new FileReader(sourceFile))) {
			List<Product> products = new ArrayList<Product>();
			String line = br.readLine();
			while (line != null) {
				String []attribute = line.split(",");
				Product prod = new Product(attribute[0], Double.parseDouble(attribute[1].trim()));
				products.add(prod);
				line = br.readLine();
			}
			
			Comparator<String> comp  = (a,b)->a.toUpperCase().compareTo(b.toUpperCase());
			
			double avgPrice = products.stream()
					.map(p->p.getPrice())
					.reduce(0.0, (a,b)->a+b)/products.size();
			System.out.println("Average price: "+ String.format("%.2f", avgPrice));
			List<String> productsList = products.stream()
					.filter(p->p.getPrice()<avgPrice)
					.map(p->p.getName())
					.sorted(comp.reversed())
					.collect(Collectors.toList());
			productsList.forEach(System.out::println);

		} catch (IOException e) {
			System.out.println("Erro: " + e.getMessage());
		}
	}
}
