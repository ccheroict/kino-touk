package cc.cinema.demo.services;

import cc.cinema.demo.entities.Screening;

import java.util.List;
import java.util.Set;

public interface IRoomService {
    List<String> getAvailableSeatCodes(Screening screening);

    boolean checkLeftReservedSeats(Screening screening, Set<String> reservingSeats);
}
