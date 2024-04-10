package me.julionxn.jueguitosclient.core.teams;

public enum TeamColor {
    RED(0xffff0000, 1f, 0f, 0f),
    YELLOW(0xffffff00, 1f, 1f, 0f),
    BLUE(0xff0000ff, 0f, 0f, 1f);

    public final int color;
    public final float red;
    public final float green;
    public final float blue;

    TeamColor(int color, float red, float green, float blue){
        this.color = color;
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

}
