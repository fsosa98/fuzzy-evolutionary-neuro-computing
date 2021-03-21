
import java.io.*;
import java.util.Scanner;

import hr.fer.zemris.fuzzy.IBinaryFunction;
import hr.fer.zemris.fuzzy.Operations;
import hr.fer.zemris.fuzzy3.AkcelFuzzySystemMin;
import hr.fer.zemris.fuzzy3.COADefuzzifier;
import hr.fer.zemris.fuzzy3.Defuzzifier;
import hr.fer.zemris.fuzzy3.FuzzySystem;
import hr.fer.zemris.fuzzy3.KormiloFuzzySystemMin;

public class Main {

	public static void main(String[] args) throws IOException {

		BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

		int L = 0, D = 0, LK = 0, DK = 0, V = 0, S = 0, akcel, kormilo;
		String line = null;

		Defuzzifier def = new COADefuzzifier();
		IBinaryFunction tNorm = Operations.product();
		
		FuzzySystem fsAkcel = new AkcelFuzzySystemMin(def, tNorm);
		FuzzySystem fsKormilo = new KormiloFuzzySystemMin(def, tNorm);

		while (true) {
			if ((line = input.readLine()) != null) {
				if (line.charAt(0) == 'K')
					break;
				Scanner s = new Scanner(line);
				L = s.nextInt();
				D = s.nextInt();
				LK = s.nextInt();
				DK = s.nextInt();
				V = s.nextInt();
				S = s.nextInt();
			}

			// fuzzy magic ...

			akcel = fsAkcel.conclude(L, D, LK, DK, V, S);
			kormilo = fsKormilo.conclude(L, D, LK, DK, V, S);
			System.out.println(akcel + " " + kormilo);
			System.out.flush();
		}
	}

}