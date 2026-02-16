package andreapascarella.u5d11.controllers;

import andreapascarella.u5d11.entities.Travel;
import andreapascarella.u5d11.exceptions.ValidationException;
import andreapascarella.u5d11.payloads.TravelDTO;
import andreapascarella.u5d11.services.TravelsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/travels")
public class TravelsController {

    private final TravelsService travelsService;

    @Autowired
    public TravelsController(TravelsService travelsService) {
        this.travelsService = travelsService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Travel createTravel(@RequestBody @Validated TravelDTO payload, BindingResult validation) {
        if (validation.hasErrors()) {
            List<String> errorsList = validation.getFieldErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .toList();
            throw new ValidationException(errorsList);
        } else {
            return this.travelsService.saveTravel(payload);
        }
    }

    @GetMapping
    public Page<Travel> findAll(@RequestParam(defaultValue = "0") int page,
                                @RequestParam(defaultValue = "10") int size,
                                @RequestParam(defaultValue = "travelDate") String orderBy,
                                @RequestParam(defaultValue = "asc") String sortCriteria) {

        return this.travelsService.findAll(page, size, orderBy, sortCriteria);
    }

    @GetMapping("/{travelId}")
    public Travel findById(@PathVariable UUID travelId) {
        return this.travelsService.findById(travelId);
    }
}
