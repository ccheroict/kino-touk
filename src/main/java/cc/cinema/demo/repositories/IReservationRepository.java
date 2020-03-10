package cc.cinema.demo.repositories;

import cc.cinema.demo.entities.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IReservationRepository extends JpaRepository<Reservation, Integer> {
}
