package cc.cinema.demo.repositories;

import cc.cinema.demo.entities.ReservedSeat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface IReservedSeatRepository extends JpaRepository<ReservedSeat, Integer> {
    @Query(value = "select rs.* from reserved_seat rs join reservation r on rs.reservation_id=r.id "
            + "join screening s on r.screening_id=s.id "
            + "where s.id=?1 and ?2<=r.expired_time",
            nativeQuery = true
    )
    List<ReservedSeat> getReservedSeat4Screening(int id, LocalDateTime expiredTime);
}
