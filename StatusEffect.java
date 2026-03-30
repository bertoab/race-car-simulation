// Joshua Staub and Lior Sapir

public enum StatusEffect {
    //FIXME: maybe play with these multipliers until theyre balanced
    CONCRETE("Concrete", 1.5), //track dependent status effect
    SNOW("Snow", 0.6), //track dependent status effect
    SAND("Sand", 0.7), //track dependent status effect
    UPHILL("Uphill", 0.5), //track dependent status effect
    DOWNHILL("Downhill", 1.5), //track dependent status effect
    MUDDIED("Muddied", 0.3), //temporary chance dependent status effect
    TIRE_POPPED("Tire Popped", 0.6); //chance dependent status effect that lasts until race end

    public final String name;
    public final double multiplier;

    StatusEffect(String name, double multiplier) {
        this.name = name;
        this.multiplier = multiplier;
    }
}
