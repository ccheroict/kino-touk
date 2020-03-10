package cc.cinema.demo.repositories;

import cc.cinema.demo.entities.Screening;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface IScreeningRepository extends JpaRepository<Screening, Integer> {
    List<Screening> findAllByShowTimeBetweenOrderByMovieNameAscShowTimeAsc(LocalDateTime start, LocalDateTime end);
}
