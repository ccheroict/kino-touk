package cc.cinema.demo.services.impl;

import cc.cinema.demo.dtos.TicketType;
import cc.cinema.demo.entities.Reservation;
import cc.cinema.demo.entities.ReservedSeat;
import cc.cinema.demo.entities.Screening;
import cc.cinema.demo.repositories.IReservationRepository;
import cc.cinema.demo.repositories.IReservedSeatRepository;
import cc.cinema.demo.services.IReservationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ReservationServiceImpl implements IReservationService {
    private final IReservationRepository reservationRepository;
    private final IReservedSeatRepository reservedSeatRepository;

    public ReservationServiceImpl(IReservationRepository reservationRepository, IReservedSeatRepository reservedSeatRepository) {
        this.reservationRepository = reservationRepository;
        this.reservedSeatRepository = reservedSeatRepository;
    }

    @Override
    public Reservation makeReservation(Screening screening, String firstName, String lastName, Map<String, String> reservedSeats) {
        Reservation reservation = new Reservation();
        reservation.setFirstName(firstName);
        reservation.setLastName(lastName);
        reservation.setScreening(screening);
        reservation.setExpiredTime(LocalDateTime.now().plusMinutes(30));
        reservation = reservationRepository.save(reservation);

        List<ReservedSeat> rsList = new ArrayList<>();
        for (Map.Entry<String, String> e : reservedSeats.entrySet()) {
            ReservedSeat rs = new ReservedSeat();
            rs.setReservation(reservation);
            rs.setSeat(e.getKey());
            rs.setPrice(TicketType.valueOf(e.getValue()).getPrice());
            rsList.add(rs);
        }
        reservation.setReservedSeats(reservedSeatRepository.saveAll(rsList));

        return reservation;
    }
}
