package wfh.status;

public interface StatusChangedListener {
    void statusChanged(String previousStatus, String newStatus);
}
