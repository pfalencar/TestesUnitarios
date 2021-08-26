package br.ce.wcaquino.servicos;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

import br.ce.wcaquino.entidades.Filme;
import br.ce.wcaquino.entidades.Locacao;
import br.ce.wcaquino.entidades.Usuario;
import br.ce.wcaquino.exceptions.FilmeSemEstoqueException;
import br.ce.wcaquino.exceptions.LocadoraException;

//O parameterized será inserido ao colocar os dados que irão guiar estes testes.
//Como essa execução será diferente de uma execução padrão do JUnit, vou alterar o TestRunner
//Agora essa classe deve rodar como se fosse um parameterized. Com a annotation o JUnit já sabe que deve tratar os
//testes desta classe de uma forma diferente.
@RunWith(Parameterized.class)
public class CalculoValorLocacaoTest {
	
	private LocacaoService service;
	
	//O Parameter serve para fazer um link das variáveis que serão utilizadas no teste com a coleção que será utilizada.

	@Parameter //seria value=0, mas não precisa escrever. Este é o primeiro registro do array
	public List<Filme> filmes;
	
	@Parameter(value=1) //valor=1, pq é o segundo registro do array no método getParametros()
	public Double valorLocacao;
	
	@Parameter(value=2)  //valor=2, pq é o terceiro parâmetro no array no método getParametros()
	public String cenario;

	
	@Before 
	public void setup() {
		this.service = new LocacaoService();
	}
	
	private static Filme filme1 = new Filme("Filme 1", 2, 4.0);
	private static Filme filme2 = new Filme("Filme 2", 2, 4.0);
	private static Filme filme3 = new Filme("Filme 3", 2, 4.0);
	private static Filme filme4 = new Filme("Filme 4", 2, 4.0);
	private static Filme filme5 = new Filme("Filme 5", 2, 4.0);
	private static Filme filme6 = new Filme("Filme 6", 2, 4.0);
	private static Filme filme7 = new Filme("Filme 7", 2, 4.0);
	
	//Definir o conjunto de dados que será testado
	//Os dados utilizados no teste devem ficar em um array
	//@Parameters informa ao JUnit que esta será a fonte de dados
	@Parameters(name="{2}") //name="Teste {index} = {2} - {1}" é como será impresso os parâmetros 1 e 2.
	public static Collection<Object[]> getParametros() {
		return Arrays.asList(new Object[][] {
			{Arrays.asList(filme1, filme2), 8.0, "2 Filmes: Sem Desconto"},
			{Arrays.asList(filme1, filme2, filme3), 11.0, "3 Filmes: 25%"},
			{Arrays.asList(filme1, filme2, filme3, filme4), 13.0, "4 Filmes: 50%"},
			{Arrays.asList(filme1, filme2, filme3, filme4, filme5), 14.0, "5 Filmes: 75%"},
			{Arrays.asList(filme1, filme2, filme3, filme4, filme5, filme6), 14.0, "6 Filmes: 100%"},
			{Arrays.asList(filme1, filme2, filme3, filme4, filme5, filme6, filme7), 18.0, "7 Filmes: Sem Desconto"}
		});
	}
	
	
	//Se eu conseguir executar essa mesma estrutura com os dados sendo validados conforme as necessidades dos testes 
	//da LocacaoServiceTest,vou conseguir com apenas esse teste o resultado de todo aquele conjunto de testes. 
	//O nome dessa técnica é: DDT - Data Driven Test (Teste Orientado a Dados)	
	@Test
	public void deveCalcularValorLocacaoConsiderandoDescontos() throws FilmeSemEstoqueException, LocadoraException {
		//cenário
		Usuario usuario = new Usuario("Patricia");		
		
		//ação
		Locacao locacao = service.alugarFilme(usuario, filmes);		
		
		//verificação
		//4+4+3+2+1= 14
		assertThat(locacao.getValor(), is(valorLocacao));		
	}
	
}
