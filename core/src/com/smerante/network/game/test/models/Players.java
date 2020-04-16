package com.smerante.network.game.test.models;

import java.util.ArrayList;

public class Players implements Comparable<Players>{
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

    @Override
    public String toString() {
        return "Players{" +
                "players=" + players +
                '}';
    }

    @Override
    public int compareTo(Players players) {
        return players.getPlayers().size() < this.getPlayers().size() ? -1 : 1;
    }
}
