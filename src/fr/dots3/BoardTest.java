package fr.dots3;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BoardTest {
	
	private Board b;
	
	@BeforeEach
	void init() {
		b = new Board();
	}
	
	/**
	 * @brief Test du constructeur
	 */
	@Test
	void testConstructeur() {
		Square[][] s = b.getSquares();
		assertEquals(s.length,Board.SIZE);
		for (int i = 0; i < s.length; i++) {
			assertEquals(s[i].length,Board.SIZE);
		}
		
		Piece[] p = b.getPieces();
		assertEquals(p.length,Board.MAX_PIECES);
		
		for (int i = 0; i < Board.MAX_PIECES; i++) {
			assertEquals(p[i].getX(),1);
			assertEquals(p[i].getOr(),'h');
		}
		assertEquals(p[0].getY(),0); //Rouge
		assertEquals(p[1].getY(),2); //Bleu
		assertEquals(p[2].getY(),1); //Blanc
	}
	
	@Test
	void testSetMoves() {
		b.setMoves(0); //Mouvements piece rouge
		Square[][] s = b.getSquares();
		assertTrue(s[0][0].hasMoveOn());
		assertEquals(Square.getNbOfMoves(),3);
		assertEquals(Square.getMove(1).getOr(),'v');
	}
	
	@Test
	void testPieceMove() {
		b.setMoves(0);
		b.move(0, 1);
		
		Square[][] s = b.getSquares();
		assertEquals(s[0][0].getPiece(),'R');
		assertEquals(s[0][1].getPiece(),'R');
		
		Piece[] p = b.getPieces();
		assertEquals(p[0].getX(),0);
		assertEquals(p[0].getY(),0);
		assertEquals(p[0].getX2(),1);
		assertEquals(p[0].getY2(),0);
		assertEquals(p[0].getOr(),'h');
	}
	
	@Test
	void testScore() {
		assertEquals(b.getScore(0),1);
		b.setMoves(0);
		b.move(0, 1);
		assertEquals(b.getScore(0),0);
	}
}
