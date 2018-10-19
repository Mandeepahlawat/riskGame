package GamePlay;

import Driver.Main;

public class gameDriver {
	
private int numOfPlayers;
private StartupPhase StartupPhase_obj;

public gameDriver(int numOfPlayers)
	{
		this.numOfPlayers = numOfPlayers;
		StartupPhase_obj = new StartupPhase(numOfPlayers);
	}
}