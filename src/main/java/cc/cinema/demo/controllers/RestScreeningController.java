package cc.cinema.demo.controllers;

import cc.cinema.demo.dtos.AvailableScreeningSeatDTO;
import cc.cinema.demo.dtos.ScreeningDTO;
import cc.cinema.demo.entities.Screening;
import cc.cinema.demo.services.IRoomService;
import cc.cinema.demo.services.IScreeningService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class RestScreeningController {

    private final IScreeningService screeningService;
    private final IRoomService roomService;

    public RestScreeningController(IScreeningService screeningService, IRoomService roomService) {
        this.screeningService = screeningService;
        this.roomService = roomService;
    }

    @GetMapping("/screenings")
    public ResponseEntity<List<ScreeningDTO>> getScreenings(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
                                                            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime startTime,
                                                            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime endTime) {
        List<Screening> res = screeningService.getScreeningsInInterval(date, startTime, endTime);
        List<ScreeningDTO> dtos = res.stream()
                .map(screening -> new ScreeningDTO(screening.getId(), screening.getMovie().getName(), screening.getShowTime()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/screenings/{id}/seats")
    public ResponseEntity<AvailableScreeningSeatDTO> getAvailableScreeningSeats(@PathVariable Integer id) {
        Screening screening = screeningService.getScreeningById(id);
        if (screening == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Screening does not exist");
        }

        AvailableScreeningSeatDTO dto = new AvailableScreeningSeatDTO();
        dto.setRoom(screening.getRoom());
        dto.setSeats(roomService.getAvailableSeatCodes(screening));
        return ResponseEntity.ok(dto);
    }
}
