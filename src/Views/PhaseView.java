package Views;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;

import Driver.Main;
import Map.Map;
import Player.Player;
import Player.Player.GamePhase;

public class PhaseView implements Observer {
	
	public void reinforcementPhaseView(Player player) {
		System.out.println("\n***********************"
							+ player.getName()
							+ " REINFORCEMENT PHASE BEGINS*****************************\n");
		player.placeReinforcements(player.calculateReinforcementArmies());
	}
	
	public void fortificationPhaseView(Player player) {
		System.out.println("\n***********************"
							+ player.getName()
							+" FORTIFICATION PHASE BEGINS*****************************\n");
		player.fortification();
	}
	
	public void attackPhaseView(Player player) {
		System.out.println("\n***********************"
							+ player.getName()
							+ " ATTACK PHASE BEGINS*****************************\n");
		player.attack();
	}
	
	@Override
	public void update(Observable o, Object arg) {
		Player currentPlayer = ((Player) o);
		if(!currentPlayer.cardExchangeViewOpen) {
			switch(currentPlayer.currentGamePhase) {
				case REINFORCEMENT:
					reinforcementPhaseView(currentPlayer);
					break;
				case ATTACK:
					attackPhaseView(currentPlayer);
					break;
				case FORTIFICATION:
					fortificationPhaseView(currentPlayer);
					break;
			}
		}
	}
	
}
