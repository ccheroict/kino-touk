package cc.cinema.demo.entities;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "reserved_seat")
@Data
public class ReservedSeat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    private Reservation reservation;
    private String seat;
    private double price;
}
