package es.parte2.ais.unitarias;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import es.codeurjc.ais.tictactoe.Board;

//Pruebas unitarias de la clase Board
public class BoardTest {

	Board board;	
	
	@BeforeEach
	public void setUp() {
		board = new Board();
		
	}
	
		
	
	//Test getCellsIfWinner(…) 
	@Test
	public void testGetCellsIfWinner() {
		assertThat("No es ganador", null, equalTo(board.getCellsIfWinner("Ana")));
		
		
	}
	

	//Test checkDraw()
	@Test
	public void testCheckDraw() {
		
		assertThat("No estan todas las casillas a false", false, equalTo(board.checkDraw()));
		board.enableAll(); //Pone todas las casillas a true, pero checkDraw comprueba el valor, no active
		assertThat("No estan todas las casillas rellenas", false ,equalTo(board.checkDraw()));
		
			
	}
	
}
