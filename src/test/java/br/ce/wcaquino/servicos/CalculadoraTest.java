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
		//cenário
		int a = 5;
		int b = 3;
		
		//ação
		int resultado = calculadora.somar(a, b);
		
		//verificação
		Assert.assertEquals(8, resultado);
	}
	
	@Test
	public void deveSubtrairDoisValores() {
		//cenário
		int a = 4;
		int b = 1;
		
		//ação
		int resultado = calculadora.subtrair(a, b);
		
		//verificação
		Assert.assertEquals(3, resultado);
	}
	
	@Test
	public void deveDividirDoisValores() throws NaoPodeDividirPorZeroException {
		//cenário
		int a = 6;
		int b = 3;
		
		//ação
		int resultado = calculadora.dividir(a, b);
		
		//verificação
		Assert.assertEquals(2, resultado);
	}
	
	@Test(expected = NaoPodeDividirPorZeroException.class)
	public void deveLancarExcecaoAoDividirPorZero() throws NaoPodeDividirPorZeroException {
		//cenário
		int a = 10;
		int b = 0;
		
		//ação
		calculadora.dividir(a, b);
		
		//verificação
		//já é feita em: (expected = NaoPodeDividirPorZeroException.class)
	}
	
	
}
