package andreapascarella.u5d11.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@Table(name = "reservations")
public class Reservation {

    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID reservationID;

    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee reservationEmployee;

    @ManyToOne
    @JoinColumn(name = "travel_id", nullable = false)
    private Travel reservationTravel;

    @Column(nullable = false)
    private LocalDate reservationDate;

    private String notes;

    public Reservation(Employee reservationEmployee, Travel reservationTravel, LocalDate reservationDate, String notes) {
        this.reservationEmployee = reservationEmployee;
        this.reservationTravel = reservationTravel;
        this.reservationDate = reservationDate;
        this.notes = notes;
    }
}
