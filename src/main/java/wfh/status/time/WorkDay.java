package wfh.status.time;

import wfh.status.Status;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Entity
public class WorkDay {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(unique = true)
    private LocalDate day;

    @OneToMany(targetEntity = StatusChange.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "work_day_id")
    private List<StatusChange> statusChanges = new ArrayList<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDate getDay() {
        return day;
    }

    public void setDay(LocalDate day) {
        this.day = day;
    }

    public List<StatusChange> getStatusChanges() {
        return statusChanges;
    }

    public void setStatusChanges(List<StatusChange> statusChanges) {
        this.statusChanges = statusChanges;
    }

    public Optional<StatusChange> getMostRecentUnfinishedStatusChange() {
        return statusChanges.stream()
                .filter((s) -> s.getStop() == null)
                .findFirst();
    }

    public long calculateTime(Status status) {
        return statusChanges.stream()
                .filter((sc) -> status.equals(sc.getStatus()))
                .mapToLong(StatusChange::getElapsedTime)
                .sum();
    }
}
