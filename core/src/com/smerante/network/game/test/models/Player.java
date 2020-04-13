package com.smerante.network.game.test.models;

/**
 * Created by Sam Merante on 2020-04-13.
 */

public class Player {
    float pX, pY, pVX, pVY;
    int playerID;

    public float getpX() {
        return pX;
    }

    public void setpX(float pX) {
        this.pX = pX;
    }

    public float getpY() {
        return pY;
    }

    public void setpY(float pY) {
        this.pY = pY;
    }

    public float getpVX() {
        return pVX;
    }

    public void setpVX(float pVX) {
        this.pVX = pVX;
    }

    public float getpVY() {
        return pVY;
    }

    public void setpVY(float pVY) {
        this.pVY = pVY;
    }

    public int getPlayerID() {
        return playerID;
    }

    public void setPlayerID(int playerID) {
        this.playerID = playerID;
    }

    @Override
    public String toString() {
        return "Player [pX=" + pX + ", pY=" + pY + ", pVX=" + pVX + ", pVY=" + pVY + ", playerID=" + playerID + "]";
    }

}
