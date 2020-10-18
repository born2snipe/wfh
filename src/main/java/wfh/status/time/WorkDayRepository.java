package wfh.status.time;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface WorkDayRepository extends CrudRepository<WorkDay, Integer> {
    Optional<WorkDay> findByDay(LocalDate date);
}
