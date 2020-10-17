package wfh.settings;

public class Settings {
    private boolean useDarkTheme = true;
    private StatusSetting afk;
    private StatusSetting lunch;
    private StatusSetting working;

    public boolean isUseDarkTheme() {
        return useDarkTheme;
    }

    public void setUseDarkTheme(boolean useDarkTheme) {
        this.useDarkTheme = useDarkTheme;
    }

    public StatusSetting getAfk() {
        return afk;
    }

    public void setAfk(StatusSetting afk) {
        this.afk = afk;
    }

    public StatusSetting getLunch() {
        return lunch;
    }

    public void setLunch(StatusSetting lunch) {
        this.lunch = lunch;
    }

    public StatusSetting getWorking() {
        return working;
    }

    public void setWorking(StatusSetting working) {
        this.working = working;
    }
}
