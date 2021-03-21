package hr.fer;

public class Neuron {

	private double value;
	private double error;

	public Neuron() {
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public double getError() {
		return error;
	}

	public void setError(double error) {
		this.error = error;
	}
}
