package Card;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import Card.Card.CardType;
import Driver.Main;
import Map.Map;
import Map.Map.Territory;
import Player.Player;
/**
 * This is the JUnit Test cases for Card class. this implements all
 * the test related to the units within this class.
 *
 * @author  Arun
 * @version 1.0
 * @since   2018-11-28 
 */
public class Card_Test {

	private Player player;
	/**
	 * test class testBefore that runs 
	 * before each methods  within this class
	 */
	@Before
	public void testBefore() {
		player = new Player("Player1");
		Main.players = new ArrayList<>();
		Main.cards=new ArrayList<>();
		Map.listOfAllTerritories=new ArrayList<>();
	}
	/**
	 * test the Card constructing method
	 */
	@Test
	public void testCardTerritory() {

		Main.cards = new ArrayList<>();
		Territory t1 = new Territory("Africa");
		Card card = new Card(t1);
		assertTrue(card.territory.name.equals("Africa"));
	}

	/**
	 * test the Card constructing method
	 */
	@Test
	public void testCardTerritoryCardType() {
		Main.cards = new ArrayList<>();
		Territory t1 = new Territory("Africa");
		Card card = new Card(t1,CardType.INFANTRY);
		assertTrue(card.type.equals(CardType.INFANTRY));
	}

	/**
	 * test the Card Assignment method
	 */
	@Test
	public void testAssignPlayer() {
		Main.cards = new ArrayList<>();
		Territory t1 = new Territory("Africa");
		Card card = new Card(t1,CardType.INFANTRY);
		card.assignPlayer(player);
		assertTrue(card.owner.equals(player));
	}

	/**
	 * test if the card can be assigned
	 */
	@Test
	public void testCanAssignedToPlayer() {
		Main.cards = new ArrayList<>();
		Territory t1 = new Territory("Africa");
		Card card = new Card(t1,CardType.INFANTRY);
		card.previousOwners=new ArrayList<>();
		assertTrue(card.canAssignedToPlayer(player));
	}
	
	/**
	 * test if the card cannot be assigned
	 */
	@Test
	public void testCanNotAssignedToPlayer() {
		Main.cards = new ArrayList<>();
		
		Territory t1 = new Territory("Africa");
		Card card = new Card(t1,CardType.INFANTRY);
		card.previousOwners = new ArrayList<Player>();
		card.previousOwners.add(player);
		assertFalse(card.canAssignedToPlayer(player));
	}
 

}
