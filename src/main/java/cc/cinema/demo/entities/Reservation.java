package cc.cinema.demo.entities;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlTransient;
import java.time.LocalDateTime;
import java.util.Collection;

@Entity
@Data
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    private Screening screening;

    private String firstName;
    private String lastName;
    private LocalDateTime expiredTime;

    @XmlTransient
    @OneToMany(mappedBy = "reservation")
    private Collection<ReservedSeat> reservedSeats;
}
