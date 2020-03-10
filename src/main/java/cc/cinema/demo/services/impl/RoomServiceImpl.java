package cc.cinema.demo.services.impl;

import cc.cinema.demo.entities.ReservedSeat;
import cc.cinema.demo.entities.Screening;
import cc.cinema.demo.repositories.IReservedSeatRepository;
import cc.cinema.demo.services.IRoomService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Service
public class RoomServiceImpl implements IRoomService {

    public static final int MAX_ROW_COUNT = 5;
    public static final int MAX_COL_COUNT = 5;

    private final IReservedSeatRepository reservedSeatRepository;

    public RoomServiceImpl(IReservedSeatRepository reservationRepository) {
        this.reservedSeatRepository = reservationRepository;
    }

    @Override
    public List<String> getAvailableSeatCodes(Screening screening) {
        List<ReservedSeat> reservedSeats = reservedSeatRepository.getReservedSeat4Screening(screening.getId(), LocalDateTime.now());
        Set<String> reservedSeatCodes = reservedSeats.stream().map(rs -> rs.getSeat()).collect(Collectors.toSet());
        List<String> res = new ArrayList<>();
        for (Integer rowIndex = 1; rowIndex <= MAX_ROW_COUNT; rowIndex++) {
            for (Integer colIndex = 1; colIndex <= MAX_COL_COUNT; colIndex++) {
                if (!reservedSeatCodes.contains(rowIndex + "" + colIndex)) {
                    res.add(rowIndex + "" + colIndex);
                }
            }
        }
        return res;
    }

    @Override
    public boolean checkLeftReservedSeats(Screening screening, Set<String> reservingSeats) {
        boolean[][] marker = new boolean[MAX_ROW_COUNT][MAX_COL_COUNT];
        List<ReservedSeat> reservedSeats = reservedSeatRepository.getReservedSeat4Screening(screening.getId(), LocalDateTime.now());
        for (ReservedSeat rs : reservedSeats) {
            Integer rowNum = rs.getSeat().charAt(0) - '1';
            Integer colNum = rs.getSeat().charAt(1) - '1';
            marker[rowNum][colNum] = true;
        }
        for (String seat : reservingSeats) {
            Integer rowNum = seat.charAt(0) - '1';
            Integer colNum = seat.charAt(1) - '1';
            if (marker[rowNum][colNum]) {
                return false;
            }
            marker[rowNum][colNum] = true;
        }
        for (Integer row = 0; row < MAX_ROW_COUNT; row++) {
            for (Integer col = 1; col < MAX_COL_COUNT - 1; col++) {
                if (!marker[row][col] && marker[row][col - 1] && marker[row][col + 1]) {
                    return false;
                }
            }
        }
        return true;
    }
}
