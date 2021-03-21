package hr.fer;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

public class Paint extends JFrame {

	private static final long serialVersionUID = 1L;

	private List<List<Point>> gestures = new ArrayList<List<Point>>();
	private List<Point> points = new ArrayList<Point>();
	private JPanel panel;
	private int M = 20;
	private int N = 20;
	private int numberOfGestures = 5;
	private boolean test;
	private NeuralNetwork neuralNetwork;

	public Paint(boolean test) {
		this.test = test;
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setTitle("Paint");
		setSize(500, 500);
		setLocationRelativeTo(null);
		initGUI();
	}

	private void initGUI() {
		panel = new JPanel();
		add(panel);

		panel.addMouseMotionListener(new MouseMotionAdapter() {

			@Override
			public void mouseDragged(MouseEvent e) {
				points.add(new Point(e.getPoint().getX(), e.getPoint().getY()));
				int n = points.size();
				if (n > 1) {
					panel.getGraphics().drawLine((int) points.get(n - 2).getX(), (int) points.get(n - 2).getY(),
							(int) points.get(n - 1).getX(), (int) points.get(n - 1).getY());
				}
				super.mouseDragged(e);
			}

		});

		panel.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseReleased(MouseEvent e) {
				processPoint(e);

				if (test) {
					List<Point> last = gestures.get(gestures.size() - 1);
					List<Double> xTest = new ArrayList<Double>();
					for (Point p : last) {
						xTest.add(p.getX());
						xTest.add(p.getY());
					}
					System.out.println(neuralNetwork.predict(xTest));
				} else {
					if (N * numberOfGestures == gestures.size()) {
						StringBuilder sb = new StringBuilder();
						for (List<Point> points2 : gestures) {
							for (Point point2 : points2) {
								sb.append(point2 + " ");
							}
							sb.append("\n");
						}
						FileWriter writer;
						try {
							writer = new FileWriter("output.txt");
							writer.write(sb.toString());
							writer.close();
						} catch (IOException e1) {
							e1.printStackTrace();
						}
					}
				}
				super.mouseClicked(e);
			}

		});
	}

	private void processPoint(MouseEvent e) {
		double averageX = points.stream().mapToDouble(a -> a.getX()).average().getAsDouble();
		double averageY = points.stream().mapToDouble(a -> a.getY()).average().getAsDouble();

		List<Point> newPoints = new ArrayList<Point>();
		double m = -10000;
		for (Point p : points) {
			Point newPoint = new Point(p.getX() - averageX, p.getY() - averageY);
			newPoints.add(newPoint);
			m = Math.max(Math.max(Math.abs(newPoint.getX()), Math.abs(newPoint.getY())), m);
		}
		points = new ArrayList<Point>(newPoints);

		double distance = 0;

		newPoints = new ArrayList<Point>();
		for (Point p : points) {
			Point newPoint = new Point(p.getX() / m, p.getY() / m);
			newPoints.add(newPoint);
			int n = newPoints.size();
			if (newPoints.size() > 1) {
				distance += Math.sqrt(Math.pow(newPoints.get(n - 2).getX() - newPoints.get(n - 1).getX(), 2)
						+ Math.pow(newPoints.get(n - 2).getY() - newPoints.get(n - 1).getY(), 2));
			}
		}
		points = new ArrayList<Point>(newPoints);

		newPoints = new ArrayList<Point>();
		for (int k = 0; k < M - 1; k++) {
			double dist = k * distance / (M - 1);
			double currentDistance = 0;
			for (int i = 1; i < points.size(); i++) {
				currentDistance += Math.sqrt(Math.pow(points.get(i - 1).getX() - points.get(i).getX(), 2)
						+ Math.pow(points.get(i - 1).getY() - points.get(i).getY(), 2));
				if (currentDistance >= dist) {
					newPoints.add(points.get(i));
					break;
				}
			}
		}
		newPoints.add(points.get(points.size() - 1));
		points = new ArrayList<Point>(newPoints);

		// points.forEach(System.out::println);
		panel.getGraphics().clearRect(0, 0, panel.getWidth(), panel.getHeight());
		gestures.add(points);
		points = new ArrayList<Point>();
	}

	public void setNeuralNetwork(NeuralNetwork neuralNetwork) {
		this.neuralNetwork = neuralNetwork;
	}

	public static void main(String[] args) {
//		SwingUtilities.invokeLater(() -> {
//			new Paint(false).setVisible(true);
//		});
		int M = 20;
		int algorithm = 2;
		int N = 20;
		int numberOfGestures = 5;
		int numberOfIterations = 2000;
		NeuralNetwork neuralNetwork = new NeuralNetwork("output.txt", M, Arrays.asList(2 * M, 10, 5, numberOfGestures),
				algorithm, numberOfGestures, N, numberOfIterations);
		neuralNetwork.train();
		Paint paint = new Paint(true);
		paint.setNeuralNetwork(neuralNetwork);
		SwingUtilities.invokeLater(() -> {
			paint.setVisible(true);
		});
	}
}