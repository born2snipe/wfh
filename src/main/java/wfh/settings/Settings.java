package wfh.settings;

public class Settings {
    private String lookAndFeelClassName;
    private StatusSetting afk;
    private StatusSetting lunch;
    private StatusSetting work;

    public String getLookAndFeelClassName() {
        return lookAndFeelClassName;
    }

    public void setLookAndFeelClassName(String lookAndFeelClassName) {
        this.lookAndFeelClassName = lookAndFeelClassName;
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

    public StatusSetting getWork() {
        return work;
    }

    public void setWork(StatusSetting work) {
        this.work = work;
    }
}
