package fr.dots3;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PlayerTest {
	
	Player p1;
	Player p2;
	
	@BeforeEach
	void init() {
		p1 = new Player(0);
		p2 = new Player(1);
	}

	@Test
	void testConstructor() {
		assertEquals(p1.getScore(),0);
		assertEquals(p2.getScore(),0);
		
		p1.setColor(0);
		assertEquals(p1.getColor(),'R');
		p2.defaultColor(p1.getColor());
		assertEquals(p2.getColor(),'B');
	}
	
	@Test
	void testScore() {
		p1.addScore(5);
		assertEquals(p1.getScore(),5);
		assertEquals(p1.getScore2()," 5");
		p1.addScore(5);
		assertEquals(p1.getScore2(),"10");
	}
}
