package com.example.myRoomInfo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MyRoomInfoApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyRoomInfoApplication.class, args);
	}
}
