package cc.cinema.demo.services;

import cc.cinema.demo.entities.Screening;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public interface IScreeningService {
    List<Screening> getScreeningsInInterval(LocalDate date, LocalTime startTime, LocalTime endTime);

    Screening getScreeningById(Integer id);
}
