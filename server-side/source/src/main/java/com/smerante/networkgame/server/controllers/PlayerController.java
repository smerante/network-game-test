package com.smerante.networkgame.server.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.smerante.networkgame.server.models.Player;
import com.smerante.networkgame.server.models.Players;
import com.smerante.networkgame.server.services.PlayerService;

@RestController
public class PlayerController {

	@Autowired
	private PlayerService playerService;

	@RequestMapping(value = "/get-players", method = RequestMethod.GET)
	public Players getPlayers() {
		return playerService.getPlayers();
	}
	
	@RequestMapping(value = "/clear-players", method = RequestMethod.GET)
	public Players clearPlayers() {
		this.playerService.getPlayers().getPlayers().clear();
		return playerService.getPlayers();
	}

	@RequestMapping(value = "/add-player", method = RequestMethod.POST)
	public ResponseEntity<Player> addPlayer(@RequestBody Player player) {
		ResponseEntity<Player> responsEntity;
		HttpHeaders responseHeaders = new HttpHeaders();

		player.setPlayerID(this.playerService.addPlayer(player));
		responseHeaders.set("Added Successful", ""+true);
		responsEntity = new ResponseEntity<Player>(player,responseHeaders,HttpStatus.CREATED);
		return responsEntity;
	}
	
	@RequestMapping(value = "/update-player-state", method = RequestMethod.POST)
	public void updatePlayerState(@RequestBody Player player) {
		playerService.updatePlayerState(player);
	}
}
