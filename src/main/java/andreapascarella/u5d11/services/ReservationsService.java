package andreapascarella.u5d11.services;

import andreapascarella.u5d11.entities.Reservation;
import andreapascarella.u5d11.exceptions.BadRequestException;
import andreapascarella.u5d11.payloads.ReservationDTO;
import andreapascarella.u5d11.repositories.ReservationsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
public class ReservationsService {

    private final ReservationsRepository reservationsRepository;
    private final TravelsService travelsService;
    private final EmployeesService employeesService;

    @Autowired
    public ReservationsService(ReservationsRepository reservationsRepository, TravelsService travelsService, EmployeesService employeesService) {
        this.reservationsRepository = reservationsRepository;
        this.travelsService = travelsService;
        this.employeesService = employeesService;
    }

    public Reservation saveReservation(ReservationDTO payload) {
        if (this.reservationsRepository.existsByReservationEmployeeAndReservationDate(this.employeesService.findById(UUID.fromString(payload.reservationEmployeeId())), payload.reservationDate())) {
            throw new BadRequestException("L'impiegato " + this.employeesService.findById(UUID.fromString(payload.reservationEmployeeId())).getUsername() + " ha giá un'altra prenotazione fissata per quel giorno");
        }

        if (this.travelsService.findById(UUID.fromString(payload.reservationTravelId())).isCompleted()) {
            throw new BadRequestException("Il viaggio per cui stai prenotando é giá passato");
        }

        if (!payload.reservationDate().equals(this.travelsService.findById(UUID.fromString(payload.reservationTravelId())).getTravelDate())) {
            throw new BadRequestException("Il viaggio per cui stai prenotando non si effettua quel giorno");
        }

        Reservation newReservation = new Reservation(this.employeesService.findById(UUID.fromString(payload.reservationEmployeeId())), this.travelsService.findById(UUID.fromString(payload.reservationTravelId())), payload.reservationDate(), payload.notes());

        Reservation savedReservation = this.reservationsRepository.save(newReservation);

        log.info("La prenotazione con id " + savedReservation.getReservationID() + " è stato salvato correttamente!");

        return savedReservation;
    }
}
