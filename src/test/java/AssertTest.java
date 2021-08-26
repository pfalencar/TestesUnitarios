

import org.junit.Assert;
import org.junit.Test;

import br.ce.wcaquino.entidades.Usuario;

public class AssertTest {
	
	@Test
	public void test() {
		
		Assert.assertTrue(true);
		Assert.assertFalse(false);
		
		Assert.assertEquals("Erro de coomparação", 2, 1);
		
		//0.01 é a margem de erro, precisa qdo se usa valores double
		Assert.assertEquals(Math.PI, 3.14, 0.01);
		Assert.assertEquals(0.5123456, 0.512378, 0.0001);
		
		int i = 5;
		Integer i2 = 5;
		Assert.assertEquals(i, i2.intValue());
		Assert.assertEquals(Integer.valueOf(i), i2);
		
		Assert.assertEquals("bola", "bola");
		Assert.assertNotEquals("bola", "casa");
		Assert.assertTrue("bola".equalsIgnoreCase("Bola"));
		Assert.assertTrue("bola".startsWith("bo"));
		
		Usuario u1 = new Usuario("Usuario 1");
		Usuario u2 = new Usuario("Usuario 1");
		Usuario u3 = null;
		
		//compara os nomes qdo o método equals foi sobreescrito na classe Usuario
		Assert.assertEquals(u1, u2);
		
		//compara as instâncias
		Assert.assertSame(u2, u2);
		Assert.assertNotSame(u1, u2);
		
		Assert.assertTrue(u3 == null);
		Assert.assertNull(u3);
		Assert.assertNotNull(u2);
	
	}

}
