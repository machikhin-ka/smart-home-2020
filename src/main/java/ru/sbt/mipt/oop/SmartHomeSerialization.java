package ru.sbt.mipt.oop;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class SmartHomeSerialization {
	public void toSerialize(SmartHome smartHome, String fileName) throws IOException {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String jsonString = gson.toJson(smartHome);
		System.out.println(jsonString);
		Path path = Paths.get(fileName);
		try (BufferedWriter writer = Files.newBufferedWriter(path)) {
			writer.write(jsonString);
		}
	}
}
