package wfh.settings;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.awt.Color;

public class StatusSetting {
    private String color;

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @JsonIgnore
    public Color getAwtColor() {
        return Color.decode(color);
    }
}
