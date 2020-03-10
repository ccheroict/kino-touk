package cc.cinema.demo.dtos;

import lombok.Data;

import java.util.List;

@Data
public class AvailableScreeningSeatDTO {
    private String room;
    private List<String> seats;
}
