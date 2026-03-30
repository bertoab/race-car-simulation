// Joshua Staub and Lior Sapir

// Represents possible status effects that can be applied to a car during a race.
public enum StatusEffect {
    CONCRETE("Concrete", 1.5, 0, 0), //track dependent status effect
    SNOW("Snow", 0.6, 0, 0), //track dependent status effect
    SAND("Sand", 0.7, 0, 0), //track dependent status effect
    UPHILL("Uphill", 0.5, 0, 0), //track dependent status effect
    DOWNHILL("Downhill", 1.5, 0, 0), //track dependent status effect
    MUDDIED("Muddied", 0.3, 0.06, 0.3), //temporary chance dependent status effect
    TIRE_POPPED("Tire Popped", 0.6, 0.015, 0); //chance dependent status effect that lasts until race end

    public final String name;
    public final double speedMultiplier;
    public final double startChancePerSec;
    public final double endChancePerSec;

    StatusEffect(String name, double speedMultiplier, double startChancePerSec, double endChancePerSec) {
        this.name = name;
        this.speedMultiplier = speedMultiplier;
        this.startChancePerSec = startChancePerSec;
        this.endChancePerSec = endChancePerSec;
    }

    @Override
    public String toString() {
        return name;
    }
}
