package ru.sbt.mipt.oop.domain;

import java.util.Collection;

public class Room implements Actionable {
    private Collection<Light> lights;
    private Collection<Door> doors;
    private String name;

    public Room(Collection<Light> lights, Collection<Door> doors, String name) {
        this.lights = lights;
        this.doors = doors;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public void execute(Action action) {
        if (lights != null) {
            lights.forEach(light -> light.execute(action));
        }
        if (doors != null) {
            doors.forEach(door -> door.execute(action));
        }
        action.act(this);
    }
}
