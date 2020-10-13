package ru.sbt.mipt.oop.deserialization;

import com.google.gson.Gson;
import ru.sbt.mipt.oop.domain.SmartHome;

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
