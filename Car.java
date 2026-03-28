// Joshua Staub

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Car {
    private String name;
    private double speed;
    private int currentTrackIndex;
    private final int goalTrackIndex;
    private final int totalTracks;
    private double positionInTrackSection;
    private final Set<StatusEffect> statusEffects;

    public Car(String name, int startTrackIndex, int goalTrackIndex, int totalTracks) {
        this.name = name;
        this.currentTrackIndex = startTrackIndex;
        this.goalTrackIndex = goalTrackIndex;
        this.totalTracks = totalTracks;
        speed = 0;
        positionInTrackSection = 0;
        statusEffects = new HashSet<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = Math.clamp(speed, 0.0, MAX_SPEED);
    }

    public double getPosition() {
        return positionInTrackSection;
    }

    public void setPosition(double position) {
        if (position < 0.0) {
            return;
        }

        if (hasFinished()) {
            this.positionInTrackSection = 0;
            return;
        }

        if (position >= 1.0) {
            // If the position is 1 or greater, then move to the next track
            int trackIncrement = Utility.floorInt(position);

            // Avoid overshooting the goal track
            int tracksRemaining = Math.floorMod(goalTrackIndex - currentTrackIndex, totalTracks);
            if (tracksRemaining <= trackIncrement) {
                this.currentTrackIndex = goalTrackIndex;
                this.positionInTrackSection = 0.0;
            } else {
                this.currentTrackIndex = (currentTrackIndex + trackIncrement) % totalTracks;
                this.positionInTrackSection = position - trackIncrement;
            }
        } else {
            this.positionInTrackSection = position;
        }
    }

    public void setCurrentTrackIndex(int trackIndex) {
        this.currentTrackIndex = trackIndex % totalTracks;
        this.positionInTrackSection = 0.0;
    }

    public int getCurrentTrackIndex() {
        return currentTrackIndex;
    }

    public boolean hasFinished() {
        return goalTrackIndex == currentTrackIndex;
    }

    public Set<StatusEffect> getStatusEffects() {
        return Collections.unmodifiableSet(statusEffects);
    }

    public void addEffect(StatusEffect statusEffect) {
        statusEffects.add(statusEffect);
    }

    public void removeEffect(StatusEffect statusEffect) {
        statusEffects.remove(statusEffect);
    }

    @Override
    public String toString() {
        return "Car (" + name + ")";
    }

    public static final double MAX_SPEED = 60;
}
