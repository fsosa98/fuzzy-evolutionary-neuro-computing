package hr.fer;

import java.io.FileWriter;
import java.io.IOException;

public class Task1 {

	public static void main(String[] args) {
		try {
			FileWriter writer1 = new FileWriter("task1_1.txt");
			FileWriter writer2 = new FileWriter("task1_025.txt");
			FileWriter writer3 = new FileWriter("task1_4.txt");
			FileWriter writer4 = new FileWriter("task1_x.txt");
			double w = 2;
			for (int x = -8; x <= 10; x++) {
				writer1.write(Util.calculateSimilarity(x, w, 1) + System.lineSeparator());
				writer2.write(Util.calculateSimilarity(x, w, 0.25) + System.lineSeparator());
				writer3.write(Util.calculateSimilarity(x, w, 4) + System.lineSeparator());
				writer4.write(x + System.lineSeparator());
			}
			writer1.close();
			writer2.close();
			writer3.close();
			writer4.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

	}

}
