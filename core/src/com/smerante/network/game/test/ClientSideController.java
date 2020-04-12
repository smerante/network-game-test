package com.smerante.network.game.test;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.Net.HttpRequest;
import com.badlogic.gdx.Net.HttpMethods;
import com.badlogic.gdx.net.HttpParametersUtils;
import com.badlogic.gdx.Net.HttpResponseListener;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;


/**
 * Created by Sam Merante on 2020-04-12.
 */
public class ClientSideController {

    public static void addPlayer(String url, Player player) {
        final String[] response = new String[1];

        HttpRequest httpPost = new HttpRequest(HttpMethods.POST);
        httpPost.setHeader("Content-Type", "application/json");
        httpPost.setUrl(url);
        ObjectMapper mapper = new ObjectMapper();
        try {
            String playerPayload = mapper.writeValueAsString(player);
            System.out.println("Sending post to " + url + " with :" + playerPayload);
            httpPost.setContent(playerPayload);
            Gdx.net.sendHttpRequest(httpPost, new HttpResponseListener() {
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
        HttpRequest httpGet = new HttpRequest(HttpMethods.GET);
        httpGet.setUrl("http://localhost:8080/get-players");
        Gdx.net.sendHttpRequest(httpGet, new HttpResponseListener() {
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

        HttpRequest httpPost = new HttpRequest(HttpMethods.POST);
        httpPost.setHeader("Content-Type", "application/json");
        httpPost.setUrl("http://localhost:8080/update-player-state");
        ObjectMapper mapper = new ObjectMapper();
        try {
            String playerPayload = mapper.writeValueAsString(player);
            httpPost.setContent(playerPayload);
            Gdx.net.sendHttpRequest(httpPost, new HttpResponseListener() {
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

//    public static String getFullResponse(HttpURLConnection con) throws IOException {
//        final StringBuilder fullResponseBuilder = new StringBuilder();
//
//        // read status and message
//        int status = con.getResponseCode();
//        fullResponseBuilder.append(status)
//                .append(" ")
//                .append(con.getResponseMessage())
//                .append("\n");
//
//
//        // read headers
//        con.getHeaderFields().entrySet().stream()
//                .filter(new Predicate<Map.Entry<String, List<String>>>() {
//                    @Override
//                    public boolean test(Map.Entry<String, List<String>> entry) {
//                        return entry.getKey() != null;
//                    }
//                })
//                .forEach(new Consumer<Map.Entry<String, List<String>>>() {
//                    @Override
//                    public void accept(Map.Entry<String, List<String>> entry) {
//                         fullResponseBuilder.append(entry.getKey()).append(": ");
//                        List headerValues = entry.getValue();
//                        Iterator it = headerValues.iterator();
//                        if (it.hasNext()) {
//                            fullResponseBuilder.append(it.next());
//                            while (it.hasNext()) {
//                                fullResponseBuilder.append(", ").append(it.next());
//                            }
//                        }
//                        fullResponseBuilder.append("\n");
//                    }
//                });
//
//        // read response content
//        if (status > 299) {
//            BufferedReader in = new BufferedReader(
//                    new InputStreamReader(con.getErrorStream()));
//            String inputLine;
//            while ((inputLine = in.readLine()) != null) {
//                fullResponseBuilder.append(inputLine);
//            }
//            in.close();
//        } else {
//            BufferedReader in = new BufferedReader(
//                    new InputStreamReader(con.getInputStream()));
//            String inputLine;
//            while ((inputLine = in.readLine()) != null) {
//                fullResponseBuilder.append(inputLine);
//            }
//            in.close();
//        }
//
//        con.disconnect();
//        return fullResponseBuilder.toString();
//    }
}