package andreapascarella.u5d11.services;

import andreapascarella.u5d11.entities.Travel;
import andreapascarella.u5d11.exceptions.BadRequestException;
import andreapascarella.u5d11.exceptions.NotFoundException;
import andreapascarella.u5d11.payloads.TravelDTO;
import andreapascarella.u5d11.repositories.TravelsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
public class TravelsService {

    private final TravelsRepository travelsRepository;

    @Autowired
    public TravelsService(TravelsRepository travelsRepository) {
        this.travelsRepository = travelsRepository;
    }

    public Travel saveTravel(TravelDTO payload) {
        if (this.travelsRepository.existsByDestinationAndTravelDate(payload.destination(), payload.travelDate())) {
            throw new BadRequestException("C'é giá un viaggio con stessa destinazione e stessa data!");
        }

        Travel newTravel = new Travel(payload.destination(), payload.travelDate());

        Travel savedTravel = this.travelsRepository.save(newTravel);

        log.info("Il viaggio con id " + savedTravel.getTravelId() + " è stato salvato correttamente!");

        return savedTravel;
    }

    public Page<Travel> findAll(int page, int size, String orderBy, String sortCriteria) {
        if (size > 100 || size < 0) size = 10;
        if (page < 0) page = 0;
        Pageable pageable = PageRequest.of(page, size,
                sortCriteria.equals("desc") ? Sort.by(orderBy).descending() : Sort.by(orderBy));
        return this.travelsRepository.findAll(pageable);
    }

    public Travel findById(UUID travelId) {
        return this.travelsRepository.findById(travelId)
                .orElseThrow(() -> new NotFoundException(travelId));
    }
}
