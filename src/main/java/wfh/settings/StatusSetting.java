package wfh.settings;

import java.awt.Color;

public class StatusSetting {
    private String color;

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Color getAwtColor() {
        return Color.decode(color);
    }
}
