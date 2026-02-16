package andreapascarella.u5d11.payloads;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record ReservationDTO(
        @NotBlank(message = "L'id dell'impiegato è un campo obbligatorio!")
        String reservationEmployeeId,

        @NotBlank(message = "L'id del viaggio è un campo obbligatorio!")
        String reservationTravelId,

        @NotNull(message = "La data del viaggio è un campo obbligatorio!")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        LocalDate reservationDate,

        String notes
) {
}
