package com.gisaklc.cursomc.domain;

public class Retangulo implements ObjetoGeometrico {

	private double altura;
	private double largura;

	@Override
	public double getPerimetro() {
		double perimetro = (2 * altura) + (2 * largura);
		return perimetro;
	}

	@Override
	public double getArea() {
		double area = (altura * largura);
		return area;
	}

}
