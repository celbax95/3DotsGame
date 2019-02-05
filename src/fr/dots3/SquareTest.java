package fr.dots3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SquareTest {

	private Square s1;
	private Square s2;
	
	private final int x2 = 1;
	private final int y2 = 1;
	
	@BeforeEach
	void init() {
		Square.removeMoves();
		s1 = new Square(0,0);
		s2 = new Square(x2,y2);
	}
	
	@Test
	void testConstructor() {
		assertEquals(s1.getX(),0);
		assertEquals(s1.getY(),0);
		assertEquals(s1.getPiece(),0);
		assertFalse(s1.hasMoveOn());
		assertEquals(s1.MovesOn(),new ArrayList<Move>());
		assertThrows(AssertionError.class, ()->{Square.getMove(0);});
	}
	
	@Test
	void testDep() {
		assertThrows(AssertionError.class, ()->{Square.addMove(0, 0, 'v');});
		Square.addMove(x2, y2, 'v');
		s2.setDep(1);
		Square.addMove(x2, y2, 'h');
		s2.setDep(2);
		assertEquals(Square.getNbOfMoves(),2);
		assertTrue(s2.hasMoveOn());
		assertEquals(String.valueOf(s2.getDep()), "1 - 2");
		Move m = Square.getMove(0);
		assertEquals(m.getX(),x2);
		assertEquals(m.getY(),y2);
		assertEquals(m.getOr(),'v');
	}
	
	@Test
	void testPiece() {
		s1.setPiece('R');
		assertEquals(s1.getPiece(),'R');
		Piece p = new Piece('b',x2,y2,'v');
		s2.setPiece(p);
		assertEquals(s2.getPiece(),'B');
		assertThrows(AssertionError.class, ()->{s1.setPiece('V');});
		
		s2.removePiece(p);
		assertEquals(s2.getPiece(),0);
	}
}
