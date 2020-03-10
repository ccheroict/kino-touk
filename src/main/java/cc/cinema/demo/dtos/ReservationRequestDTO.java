package cc.cinema.demo.dtos;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Map;

@Data
public class ReservationRequestDTO {
    private Integer screeningId;
    private Map<String, String> reservedSeats;
    @Size(min = 3)
    @Pattern(regexp = "^[A-ZĄĆĘŁŃÓŚŹŻ][a-ząćęłńóśźż]*$")
    private String firstName;
    @Size(min = 3)
    @Pattern(regexp = "^[A-ZĄĆĘŁŃÓŚŹŻ][a-ząćęłńóśźż]*(-[A-ZĄĆĘŁŃÓŚŹŻ][a-ząćęłńóśźż]*)?$")
    private String lastName;

    public interface  ValidationGroup {}
}
