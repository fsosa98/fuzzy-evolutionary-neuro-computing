package hr.fer;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class Dataset {

	private List<double[]> data = new ArrayList<double[]>();

	public Dataset(String path) {
		try {
			List<String> lines = Files.readAllLines(new File(path).toPath(), Charset.defaultCharset());
			for (String line : lines) {
				double[] row = new double[5];
				int i = 0;
				for (String part : line.split("\\s+")) {
					row[i++] = Double.parseDouble(part);
				}
				data.add(row);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public double[] getInput(int index, int n) {
		double[] input = new double[n];
		for (int i = 0; i < n; i++) {
			input[i] = data.get(index)[i];
		}
		return input;
	}

	public double[] getData(int index) {
		return data.get(index);
	}

	public int getSize() {
		return data.size();
	}

}
