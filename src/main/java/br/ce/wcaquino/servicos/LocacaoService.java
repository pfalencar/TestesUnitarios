package br.ce.wcaquino.servicos;

import static br.ce.wcaquino.utils.DataUtils.adicionarDias;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import br.ce.wcaquino.entidades.Filme;
import br.ce.wcaquino.entidades.Locacao;
import br.ce.wcaquino.entidades.Usuario;
import br.ce.wcaquino.exceptions.FilmeSemEstoqueException;
import br.ce.wcaquino.exceptions.LocadoraException;
import br.ce.wcaquino.utils.DataUtils;

public class LocacaoService {
	
//Essas variáveis são visíveis (exceto a private) na classe LocacaoServiceTest, pois têm o mesmo nome de pacote.
//	public String vPublica;
//	String vPackage;
//	protected String vProtected;
//	private String vPrivate;
	
	public Locacao alugarFilme(Usuario usuario, List<Filme> filmes) throws FilmeSemEstoqueException, LocadoraException {
		
		if(usuario == null) {
			throw new LocadoraException("Usuário vazio");
		}
		
		if (filmes == null || filmes.isEmpty()) {
			throw new LocadoraException("Lista de Filmes Vazia");
		}		
		
		for (Filme filme2 : filmes) {
			if (filme2.getEstoque() == 0) {
				throw new FilmeSemEstoqueException();
			}
			
		}
		
		Locacao locacao = new Locacao();
		locacao.setFilmes(filmes);
		locacao.setUsuario(usuario);
		locacao.setDataLocacao(new Date());
		
		Double valorTotal = 0d;
		
		for (int i = 0; i < filmes.size(); i++) {
			
			double valorFilme = filmes.get(i).getPrecoLocacao();
			
			switch(i) {
				case 2 : valorFilme = 0.75 * valorFilme; break;
				case 3 : valorFilme = 0.50 * valorFilme; break;
				case 4 : valorFilme = 0.25 * valorFilme; break;
				case 5 : valorFilme = 0; break;
			}
			
			valorTotal += valorFilme;
		}
		locacao.setValor(valorTotal);

		//Entrega no dia seguinte
		Date dataEntrega = new Date();
		dataEntrega = adicionarDias(dataEntrega, 1);
		if (DataUtils.verificarDiaSemana(dataEntrega, Calendar.SUNDAY)) {
			dataEntrega = adicionarDias(dataEntrega, 1);
		}
		locacao.setDataRetorno(dataEntrega);
		
		//Salvando a locacao...	
		//TODO adicionar mÃ©todo para salvar
		
		return locacao;
	}

	
}