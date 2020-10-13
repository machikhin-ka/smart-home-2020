package ru.sbt.mipt.oop.domain;

import java.util.ArrayList;
import java.util.Collection;

public class SmartHome implements Actionable {
    private final Collection<Room> rooms;
    private final Signaling signaling = new Signaling();

    public SmartHome() {
        rooms = new ArrayList<>();
    }

    public SmartHome(Collection<Room> rooms) {
        this.rooms = rooms;
    }

    public void addRoom(Room room) {
        rooms.add(room);
    }

    @Override
    public void execute(Action action) {
        signaling.execute(action);
        if (rooms != null) {
            rooms.forEach(room -> room.execute(action));
        }
    }
}