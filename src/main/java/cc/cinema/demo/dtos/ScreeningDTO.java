package cc.cinema.demo.dtos;


import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ScreeningDTO {
    private Integer id;
    private String movie;
    private LocalDateTime showTime;

    public ScreeningDTO(Integer id, String movie, LocalDateTime showTime) {
        this.id = id;
        this.movie = movie;
        this.showTime = showTime;
    }
}
