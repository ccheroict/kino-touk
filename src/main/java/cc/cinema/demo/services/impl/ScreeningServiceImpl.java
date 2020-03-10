package cc.cinema.demo.services.impl;

import cc.cinema.demo.entities.Screening;
import cc.cinema.demo.services.IScreeningService;
import cc.cinema.demo.repositories.IScreeningRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
public class ScreeningServiceImpl implements IScreeningService {
    private final IScreeningRepository screeningRepository;

    public ScreeningServiceImpl(IScreeningRepository screeningRepository) {
        this.screeningRepository = screeningRepository;
    }

    @Override
    public List<Screening> getScreeningsInInterval(LocalDate date, LocalTime startTime, LocalTime endTime) {
        List<Screening> screenings = screeningRepository.findAllByShowTimeBetweenOrderByMovieNameAscShowTimeAsc(LocalDateTime.of(date, startTime), LocalDateTime.of(date, endTime));
        return screenings;
    }

    @Override
    public Screening getScreeningById(Integer id) {
        return screeningRepository.findById(id).orElse(null);
    }
}
