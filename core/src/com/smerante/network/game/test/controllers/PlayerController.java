package com.smerante.network.game.test.controllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.smerante.network.game.test.Source;
import com.smerante.network.game.test.models.Player;

/**
 * Created by Sam Merante on 2020-04-13.
 */
public class PlayerController {

    public static void addPlayer(String url, Player player) {
        final String[] response = new String[1];

        Net.HttpRequest httpPost = new Net.HttpRequest(Net.HttpMethods.POST);
        httpPost.setHeader("Content-Type", "application/json");
        httpPost.setUrl(url);
        ObjectMapper mapper = new ObjectMapper();
        try {
            String playerPayload = mapper.writeValueAsString(player);
            System.out.println("Sending post to " + url + " with :" + playerPayload);
            httpPost.setContent(playerPayload);
            Gdx.net.sendHttpRequest(httpPost, new Net.HttpResponseListener() {
                public void handleHttpResponse(Net.HttpResponse httpResponse) {
                    response[0] = httpResponse.getResultAsString();
                    Source.playerResQueue.add(response[0]);
                }

                public void failed(Throwable t) {
                    response[0] = "failed";
                }

                @Override
                public void cancelled() {
                }
            });
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    public static void getOtherPlayers() {
        final String[] response = new String[1];
        Net.HttpRequest httpGet = new Net.HttpRequest(Net.HttpMethods.GET);
        httpGet.setUrl("http://localhost:8080/get-players");
        Gdx.net.sendHttpRequest(httpGet, new Net.HttpResponseListener() {
            public void handleHttpResponse(Net.HttpResponse httpResponse) {
                response[0] = httpResponse.getResultAsString();
                Source.otherPlayersResQueue.add(response[0]);
            }

            public void failed(Throwable t) {
                response[0] = "failed";
            }

            @Override
            public void cancelled() {
            }
        });
    }


    public static void updatePlayerState(Player player) {
        final String[] response = new String[1];

        Net.HttpRequest httpPost = new Net.HttpRequest(Net.HttpMethods.POST);
        httpPost.setHeader("Content-Type", "application/json");
        httpPost.setUrl("http://localhost:8080/update-player-state");
        ObjectMapper mapper = new ObjectMapper();
        try {
            String playerPayload = mapper.writeValueAsString(player);
            httpPost.setContent(playerPayload);
            Gdx.net.sendHttpRequest(httpPost, new Net.HttpResponseListener() {
                public void handleHttpResponse(Net.HttpResponse httpResponse) {
                    response[0] = httpResponse.getResultAsString();
//                    Source.playerResQueue.add(response[0]);
                }

                public void failed(Throwable t) {
                    response[0] = "failed";
                }

                @Override
                public void cancelled() {
                }
            });
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
