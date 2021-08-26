

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;


//Qdo um teste depende do resultado do outro. Porém, melhor deixá-los independente
//rodar os testes na ordem que eu quero (ascendente neste caso)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class OrdemTest {
	
	private static int contador = 0;
	
	@Test
	public void inicia() {
		contador++;
	}
	
	@Test
	public void verifica() {
		Assert.assertEquals(1, contador);
	}

}
