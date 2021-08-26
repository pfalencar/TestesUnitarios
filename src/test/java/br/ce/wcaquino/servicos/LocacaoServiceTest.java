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
	
	
	//Antes de cada teste (cada método de teste)
	@Before  
	public void setup() {
		service = new LocacaoService();
	}
	
	@Test
	public void deveAlugarFilmeComSucesso() throws Exception {		
		Assume.assumeFalse(DataUtils.verificarDiaSemana(new Date(), Calendar.SATURDAY));
		
		//cenário
		Usuario usuario = new Usuario("Patricia");		
		List<Filme> filmes = Arrays.asList(new Filme("Jurassic Park", 2, 5.00));	
		
		//ação
		Locacao locacao = service.alugarFilme(usuario, filmes);
			
		//verificação
//		Assert.assertEquals( 5.0, locacao.getValor(), 0.01 );
		error.checkThat(locacao.getValor(), is(equalTo(5.0)));
//		assertThat(locacao.getValor(), is(not(6.0)));
		
		error.checkThat(DataUtils.isMesmaData( locacao.getDataLocacao(), new Date()), is(true) );		
		error.checkThat(isMesmaData(locacao.getDataRetorno(), obterDataComDiferencaDias(1)), is(true) );
			
	}
	
	
	
	//forma elegante de tratar exceção. Funciona bem qdo vc consegue garantir q a exceção é lançada apenas por aquele motivo.
	@Test(expected=FilmeSemEstoqueException.class)   
	public void deveLancarExcecaoAoAlugarFilmeSemEstoque() throws Exception {				
		//cenário
		Usuario usuario = new Usuario("Patricia");
		List<Filme> filmes = Arrays.asList(new Filme("Jurassic Park", 0, 5.00));	
		
		//ação
		Locacao locacao = service.alugarFilme(usuario, filmes);	
	}
	
	

	//DECORAR ESTE, PQ SERVE EM TODOS OS CASOS!
	//forma robusta de tratar a exceção. É a q vou ter mais poder sobre a execução. Se vc precisa da msg de erro.
	@Test    
	public void naoDeveAlugarFilmeSemUsuario( ) throws FilmeSemEstoqueException {
		//cenário
		List<Filme> filmes = Arrays.asList(new Filme("O poderoso chefinho II", 2, 8.00));	
		
		//ação
		try {
			service.alugarFilme(null, filmes);
			Assert.fail();
		} catch (LocadoraException e) {
			assertThat(e.getMessage(), is("Usuário vazio"));
		}		
		
		System.out.println("Forma robusta");
	}
	
	
	
	//forma nova de tratar a exceção. Se vc precisa da mensagem de erro além de saber qual o erro.
	@Test     
	public void naoDeveAlugarFilmeSemFilme() throws FilmeSemEstoqueException, LocadoraException {
		
		//cenário
		Usuario usuario = new Usuario("Patricia");	
		
		
		//o teste está comparando se o resultado deste teste deu LocadoraException
		exception.expect(LocadoraException.class);
		//espera-se como mensagem "Filme Vazio", tem que ser igualzinho a mensagem lançada pra o filme == null do método alugarFilme.
		exception.expectMessage("Lista de Filmes Vazia");
		
		
		//ação
		service.alugarFilme(usuario, null);
		
		System.out.println("Forma nova"); //não imprime, pq neste teste não chega até essa linha.

	}

	
	@Test
	public void deveDevolverFilmeNaSegundaAoAlugarNoSábado() throws FilmeSemEstoqueException, LocadoraException {
		Assume.assumeTrue(DataUtils.verificarDiaSemana(new Date(), Calendar.SATURDAY));
		
		//cenario
		Usuario usuario = new Usuario("Patricia");
		List<Filme> filmes = Arrays.asList(new Filme("Filme 1", 2, 4.00));		
		
		//ação
		Locacao locacao = service.alugarFilme(usuario, filmes);
		
		//verificacao
		boolean isMonday = DataUtils.verificarDiaSemana(locacao.getDataRetorno(), Calendar.MONDAY);
		Assert.assertTrue(isMonday);
	}

}

