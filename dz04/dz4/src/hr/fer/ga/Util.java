package hr.fer.ga;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class Util {

	public static List<List<Double>> input = new ArrayList<List<Double>>();
	
	private static void parseLine(String line){
		List<Double> parsedLine = new ArrayList<Double>();
		for (String number : line.split("\\s+")) {
			parsedLine.add(Double.parseDouble(number));
		}
		input.add(parsedLine);
	}

	public static void loadData(String path) {
		try (Stream<String> stream = Files.lines((new File(path)).toPath())) {
            stream.forEach(Util::parseLine);
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
}
