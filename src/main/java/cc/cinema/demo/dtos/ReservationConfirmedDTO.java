package cc.cinema.demo.dtos;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReservationConfirmedDTO {
    private double total;
    private LocalDateTime expiredTime;

    public ReservationConfirmedDTO(double total, LocalDateTime expiredTime) {
        this.total = total;
        this.expiredTime = expiredTime;
    }
}
