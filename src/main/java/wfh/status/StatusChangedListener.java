package wfh.status;

public interface StatusChangedListener {
    void statusChanged(Status previousStatus, Status newStatus);
}
