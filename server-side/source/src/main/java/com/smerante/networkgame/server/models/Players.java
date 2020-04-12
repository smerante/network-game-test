package com.smerante.networkgame.server.models;

import java.util.ArrayList;

public class Players {
	private ArrayList<Player> players;

	public Players() {
		this.players = new ArrayList<Player>();
	}

	public ArrayList<Player> getPlayers() {
		return players;
	}

	public void setPlayers(ArrayList<Player> players) {
		this.players = players;
	}

	public void addPlayer(Player player) {
		this.players.add(player);
	}
}
