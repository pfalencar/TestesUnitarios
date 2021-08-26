package br.ce.wcaquino.servicos;

import static br.ce.wcaquino.utils.DataUtils.isMesmaData;
import static br.ce.wcaquino.utils.DataUtils.obterDataComDiferencaDias;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Assume;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;

import br.ce.wcaquino.entidades.Filme;
import br.ce.wcaquino.entidades.Locacao;
import br.ce.wcaquino.entidades.Usuario;
import br.ce.wcaquino.exceptions.FilmeSemEstoqueException;
import br.ce.wcaquino.exceptions.LocadoraException;
import br.ce.wcaquino.utils.DataUtils;

public class LocacaoServiceTest {
	
	private LocacaoService service;

	@Rule
	public ErrorCollector error = new ErrorCollector();	
	@Rule
	public ExpectedException exception = ExpectedException.none();
	
	
	//Antes de cada teste (cada m�todo de teste)
	@Before  
	public void setup() {
		service = new LocacaoService();
	}
	
	@Test
	public void deveAlugarFilmeComSucesso() throws Exception {		
		Assume.assumeFalse(DataUtils.verificarDiaSemana(new Date(), Calendar.SATURDAY));
		
		//cen�rio
		Usuario usuario = new Usuario("Patricia");		
		List<Filme> filmes = Arrays.asList(new Filme("Jurassic Park", 2, 5.00));	
		
		//a��o
		Locacao locacao = service.alugarFilme(usuario, filmes);
			
		//verifica��o
//		Assert.assertEquals( 5.0, locacao.getValor(), 0.01 );
		error.checkThat(locacao.getValor(), is(equalTo(5.0)));
//		assertThat(locacao.getValor(), is(not(6.0)));
		
		error.checkThat(DataUtils.isMesmaData( locacao.getDataLocacao(), new Date()), is(true) );		
		error.checkThat(isMesmaData(locacao.getDataRetorno(), obterDataComDiferencaDias(1)), is(true) );
			
	}
	
	
	
	//forma elegante de tratar exce��o. Funciona bem qdo vc consegue garantir q a exce��o � lan�ada apenas por aquele motivo.
	@Test(expected=FilmeSemEstoqueException.class)   
	public void deveLancarExcecaoAoAlugarFilmeSemEstoque() throws Exception {				
		//cen�rio
		Usuario usuario = new Usuario("Patricia");
		List<Filme> filmes = Arrays.asList(new Filme("Jurassic Park", 0, 5.00));	
		
		//a��o
		Locacao locacao = service.alugarFilme(usuario, filmes);	
	}
	
	

	//DECORAR ESTE, PQ SERVE EM TODOS OS CASOS!
	//forma robusta de tratar a exce��o. � a q vou ter mais poder sobre a execu��o. Se vc precisa da msg de erro.
	@Test    
	public void naoDeveAlugarFilmeSemUsuario( ) throws FilmeSemEstoqueException {
		//cen�rio
		List<Filme> filmes = Arrays.asList(new Filme("O poderoso chefinho II", 2, 8.00));	
		
		//a��o
		try {
			service.alugarFilme(null, filmes);
			Assert.fail();
		} catch (LocadoraException e) {
			assertThat(e.getMessage(), is("Usu�rio vazio"));
		}		
		
		System.out.println("Forma robusta");
	}
	
	
	
	//forma nova de tratar a exce��o. Se vc precisa da mensagem de erro al�m de saber qual o erro.
	@Test     
	public void naoDeveAlugarFilmeSemFilme() throws FilmeSemEstoqueException, LocadoraException {
		
		//cen�rio
		Usuario usuario = new Usuario("Patricia");	
		
		
		//o teste est� comparando se o resultado deste teste deu LocadoraException
		exception.expect(LocadoraException.class);
		//espera-se como mensagem "Filme Vazio", tem que ser igualzinho a mensagem lan�ada pra o filme == null do m�todo alugarFilme.
		exception.expectMessage("Lista de Filmes Vazia");
		
		
		//a��o
		service.alugarFilme(usuario, null);
		
		System.out.println("Forma nova"); //n�o imprime, pq neste teste n�o chega at� essa linha.

	}

	
	@Test
	public void deveDevolverFilmeNaSegundaAoAlugarNoS�bado() throws FilmeSemEstoqueException, LocadoraException {
		Assume.assumeTrue(DataUtils.verificarDiaSemana(new Date(), Calendar.SATURDAY));
		
		//cenario
		Usuario usuario = new Usuario("Patricia");
		List<Filme> filmes = Arrays.asList(new Filme("Filme 1", 2, 4.00));		
		
		//a��o
		Locacao locacao = service.alugarFilme(usuario, filmes);
		
		//verificacao
		boolean isMonday = DataUtils.verificarDiaSemana(locacao.getDataRetorno(), Calendar.MONDAY);
		Assert.assertTrue(isMonday);
	}

}

