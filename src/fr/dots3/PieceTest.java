package fr.dots3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PieceTest {
	
	private Piece p;
	
	@BeforeEach
	void init() {
		p = new Piece('R',1,1,'h');
	}
	
	@Test
	void testConstructor() {
		Piece p = new Piece(0);
		assertEquals(p.getColor(),'R');
		assertEquals(p.getX(),1);
		assertEquals(p.getY(),0);
		assertEquals(p.getOr(),'h');
		p = new Piece(1);
		assertEquals(p.getColor(),'B');
		assertEquals(p.getY(),2);
		assertEquals(p.getX2(),2);
		assertEquals(p.getY2(),2);
	}
	
	@Test
	void testMove() {
		final Move m1 = new Move(2,0,'v');
		assertThrows(AssertionError.class, ()->{p.move(m1);});
		final Move m2 = new Move(2,0,'h');
		assertThrows(AssertionError.class, ()->{p.move(m2);});
		
		final int mx = 0, my = 1;
		
		Move m = new Move(mx,my,'v');
		p.move(m);
		assertEquals(p.getX(),mx);
		assertEquals(p.getY(),my);
		assertEquals(p.getX2(),mx);
		assertEquals(p.getY2(),my-1);
		assertEquals(p.getOr(),'v');
	}
}
