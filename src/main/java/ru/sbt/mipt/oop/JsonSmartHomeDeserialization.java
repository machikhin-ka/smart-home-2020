package ru.sbt.mipt.oop;

import com.google.gson.Gson;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class JsonSmartHomeDeserialization implements SmartHomeDeserialization {
	private final String fileName;

	public JsonSmartHomeDeserialization(String fileName) {
		this.fileName = fileName;
	}

	public SmartHome deserialize() {
		Gson gson = new Gson();
		String json = null;
		try {
			json = new String(Files.readAllBytes(Paths.get(fileName)));
		} catch (IOException e) {
			throw new RuntimeException("Could not read home from file " + fileName, e);
		}
		return gson.fromJson(json, SmartHome.class);
	}
}
