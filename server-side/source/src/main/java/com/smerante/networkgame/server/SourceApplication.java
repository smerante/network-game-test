package com.smerante.networkgame.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class SourceApplication {

	public static void main(String[] args) {
		ApplicationContext ctx = SpringApplication.run(SourceApplication.class, args);
	}

}
