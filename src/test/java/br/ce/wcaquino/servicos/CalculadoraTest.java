package br.ce.wcaquino.servicos;

import org.junit.Before;
import org.junit.Test;

import br.ce.wcaquino.exceptions.NaoPodeDividirPorZeroException;
import junit.framework.Assert;

public class CalculadoraTest {

	private Calculadora calculadora;
	
	@Before
	public void setup() {
		this.calculadora = new Calculadora();
	}
	
	@Test
	public void deveSomarDoisValores() {
		//cen�rio
		int a = 5;
		int b = 3;
		
		//a��o
		int resultado = calculadora.somar(a, b);
		
		//verifica��o
		Assert.assertEquals(8, resultado);
	}
	
	@Test
	public void deveSubtrairDoisValores() {
		//cen�rio
		int a = 4;
		int b = 1;
		
		//a��o
		int resultado = calculadora.subtrair(a, b);
		
		//verifica��o
		Assert.assertEquals(3, resultado);
	}
	
	@Test
	public void deveDividirDoisValores() throws NaoPodeDividirPorZeroException {
		//cen�rio
		int a = 6;
		int b = 3;
		
		//a��o
		int resultado = calculadora.dividir(a, b);
		
		//verifica��o
		Assert.assertEquals(2, resultado);
	}
	
	@Test(expected = NaoPodeDividirPorZeroException.class)
	public void deveLancarExcecaoAoDividirPorZero() throws NaoPodeDividirPorZeroException {
		//cen�rio
		int a = 10;
		int b = 0;
		
		//a��o
		calculadora.dividir(a, b);
		
		//verifica��o
		//j� � feita em: (expected = NaoPodeDividirPorZeroException.class)
	}
	
	
}
