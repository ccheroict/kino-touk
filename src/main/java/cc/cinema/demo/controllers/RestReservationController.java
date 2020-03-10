package cc.cinema.demo.controllers;

import cc.cinema.demo.dtos.ReservationConfirmedDTO;
import cc.cinema.demo.dtos.ReservationRequestDTO;
import cc.cinema.demo.entities.Reservation;
import cc.cinema.demo.entities.ReservedSeat;
import cc.cinema.demo.entities.Screening;
import cc.cinema.demo.services.IReservationService;
import cc.cinema.demo.services.IRoomService;
import cc.cinema.demo.services.IScreeningService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@RestController
public class RestReservationController {
    private final IReservationService reservationService;
    private final IScreeningService screeningService;
    private final IRoomService roomService;

    public RestReservationController(IReservationService reservationService, IScreeningService screeningService, IRoomService roomService) {
        this.reservationService = reservationService;
        this.screeningService = screeningService;
        this.roomService = roomService;
    }

    @PostMapping("/reservations")
    @Transactional
    public ResponseEntity<ReservationConfirmedDTO> makeReservation(@Validated @RequestBody ReservationRequestDTO request) {
        Screening screening = screeningService.getScreeningById(request.getScreeningId());
        Set<String> availableSeatCodes = new HashSet<>();
        availableSeatCodes.addAll(roomService.getAvailableSeatCodes(screening));
        if (screening == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Screening does not exist");
        }
        if (LocalDateTime.now().plusMinutes(15).isAfter(screening.getShowTime())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Please make reservation at least 15min before screening");
        }
        if (!availableSeatCodes.containsAll(request.getReservedSeats().keySet())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Selected seat has been reserved");
        }
        if (!roomService.checkLeftReservedSeats(screening, request.getReservedSeats().keySet())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cannot make reservation with free seat between reserved seat");
        }

        Reservation reservation = reservationService.makeReservation(screening, request.getFirstName(), request.getLastName(), request.getReservedSeats());
        double total = 0;

        for (ReservedSeat rs : reservation.getReservedSeats()) {
            total += rs.getPrice();
        }

        ReservationConfirmedDTO dto = new ReservationConfirmedDTO(total, reservation.getExpiredTime());

        return ResponseEntity.ok(dto);
    }
}
