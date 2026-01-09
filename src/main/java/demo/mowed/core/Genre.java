package demo.mowed.core;

public enum Genre {
    FICTION(1),
    CHILDRENS(2),
    FANTASY(3),
    MYSTERY(4),
    HISTORY(5),
    HEALTH(6),
    BIOGRAPHY(7),
    HOBBIES(8),
    SELFHELP(9),
    ROMANCE(10);

    private final int code;
    Genre(int code){
        this.code = code;
    }

    public static Genre fromCode(int code) {
        for (Genre g : values()){
            // cycle through each enum, check for match
            if (g.code == code) {
                return g;
            }
        }
        throw new IllegalArgumentException("Unknown genre: " + code);
    }
}
