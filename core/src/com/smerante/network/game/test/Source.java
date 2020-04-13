package com.smerante.network.game.test;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.smerante.network.game.test.controllers.PlayerController;
import com.smerante.network.game.test.models.Player;
import com.smerante.network.game.test.models.Players;

import java.util.ArrayList;
import java.util.PriorityQueue;

public class Source extends Game implements Screen, InputProcessor {
    public static int screenW = 1500, screenH = 800;
    SpriteBatch batch;
    ShapeRenderer shapes;
    Color playerColor, otherPlayersColor;
    Players players;
    Player player;
    float pVX, pVY = 0;
    float updateTicker = 0;
    public static PriorityQueue<String> playerResQueue = new PriorityQueue<>();
    public static PriorityQueue<String> otherPlayersResQueue = new PriorityQueue<>();

    @Override
    public void create() {
        setScreen(new Source());
    }


    @Override
    public void show() {
        batch = new SpriteBatch();
        shapes = new ShapeRenderer();
        shapes.setAutoShapeType(true);
        Gdx.input.setInputProcessor(this);

        player = new Player();
        players = new Players();
        player.setpX(screenW / 2);
        player.setpY(screenH / 2);
        player.setPlayerID(-1);

        playerColor = new Color(0, 0, 0, 1);
        otherPlayersColor = new Color(1, 0, 0, 1);

        connectToServer();
    }

    void connectToServer() {
        PlayerController.addPlayer("http://localhost:8080/add-player", this.player);
    }

    void update(float delta) {
//        updateTicker += delta;

//        if (this.updateTicker >= 0.1) {
            if(player.getPlayerID() >= 0)
                sendPlayerState();
            retrieveOtherPlayerStates();
//            updateTicker = 0;
//        }

        updateClientPlayer(delta);

        if (!playerResQueue.isEmpty()) {
            this.updatePlayerInformation();
        }

        if (!otherPlayersResQueue.isEmpty()) {
            this.updateOtherPlayerInformation();
        }
    }

    void sendPlayerState() {
//        System.out.println("send player state to update players");
        PlayerController.updatePlayerState(player);
    }

    void retrieveOtherPlayerStates() {
//        System.out.println("Retrieve updated player states");
        PlayerController.getOtherPlayers();
    }

    void updateOtherPlayerInformation() {
        new Thread(new Runnable() {
            public void run() {
                String response = otherPlayersResQueue.poll();
                ObjectMapper mapper = new ObjectMapper();
                try {
//                    System.out.println("got other players state response: " + response);
                    Players updatedPlayers = mapper.readValue(response, Players.class);
//                    System.out.println("Got updated players: " + updatedPlayers);
                    players.setPlayers(updatedPlayers.getPlayers());
//                    System.out.println(players.getPlayers());
                } catch (Exception e) {
//                    System.out.println(e);
                }
            }
        }).start();
    }

    void updatePlayerInformation() {
        new Thread(new Runnable() {
            public void run() {
                String response = playerResQueue.poll();
                ObjectMapper mapper = new ObjectMapper();
                try {
//                    System.out.println("got player update response: " + response);
                    Player updatedPlayer = mapper.readValue(response, Player.class);
                    player = updatedPlayer;
                } catch (Exception e) {
//                    System.out.println(e);
                }
            }
        }).start();
    }

    void updateClientPlayer(float delta) {
        player.setpVX(pVX * delta);
        player.setpVY(pVY * delta);
        player.setpX(player.getpX() + player.getpVX());
        player.setpY(player.getpY() + player.getpVY());
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();

        batch.end();
        shapes.begin();
        shapes.set(ShapeType.Filled);
        shapes.setColor(playerColor);
        if (player.getPlayerID() >= 0) {
            shapes.rect(player.getpX(), player.getpY(), 10, 10);
            shapes.setColor(otherPlayersColor);
            for (Player otherPlayer : players.getPlayers()) {
                if (otherPlayer.getPlayerID() != player.getPlayerID()) {
                    shapes.rect(otherPlayer.getpX(), otherPlayer.getpY(), 10, 10);
                }
            }
        }


        shapes.end();
        update(delta);
    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.D)
            pVX = 100;
        if (keycode == Input.Keys.A)
            pVX = -100;
        if (keycode == Input.Keys.S)
            pVY = -100;
        if (keycode == Input.Keys.W)
            pVY = 100;
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        if (keycode == Input.Keys.D || keycode == Input.Keys.A)
            pVX = 0;
        if (keycode == Input.Keys.S || keycode == Input.Keys.W)
            pVY = 0;
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }


    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
    }
}

//        new Thread(new Runnable() {
//            public void run() {
//                try {
//                    System.out.println("setting up connection");
//                    URL url = new URL("http://localhost:8080/hello");
//                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
//                    con.setRequestMethod("GET");
//                    con.setRequestProperty("Content-Type", "application/json");
//                    con.setConnectTimeout(5000);
//                    con.setReadTimeout(5000);
//
//                    String response = FullResponseBuilder.getFullResponse(con);
//
//                    System.out.println(response);
//                } catch (Exception e) {
//                    System.out.println("Caught exception connecting to server: " + e);
//                }
//            }
//        }).start();