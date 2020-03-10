package cc.cinema.demo.services;

import cc.cinema.demo.entities.Reservation;
import cc.cinema.demo.entities.Screening;

import java.util.Map;

public interface IReservationService {
    Reservation makeReservation(Screening screening, String firstName, String lastName, Map<String, String> reservedSeats);
}
