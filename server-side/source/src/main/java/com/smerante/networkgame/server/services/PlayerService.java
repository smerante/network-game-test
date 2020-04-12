package com.smerante.networkgame.server.services;


import org.springframework.stereotype.Component;

import com.smerante.networkgame.server.models.Player;
import com.smerante.networkgame.server.models.Players;

@Component
public class PlayerService {
	Players players;

	public PlayerService() {
		super();
		this.players = new Players();
	}

	public Players getPlayers() {
		return players;
	}

	public void setPlayers(Players players) {
		this.players = players;
	}
	
	public int addPlayer(Player player) {
		int playerID = this.players.getPlayers().size();
		player.setPlayerID(playerID);
		this.players.addPlayer(player);
		return playerID;
	}
	
	public void updatePlayerState(Player player) {
		float x = player.getpX() + player.getpVX();
		float y = player.getpY() + player.getpVY();
		this.players.getPlayers().get(player.getPlayerID()).setpX(x);
		this.players.getPlayers().get(player.getPlayerID()).setpY(y);
	}

}