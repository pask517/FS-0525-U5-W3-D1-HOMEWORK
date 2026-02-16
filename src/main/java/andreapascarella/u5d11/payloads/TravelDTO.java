package andreapascarella.u5d11.payloads;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record TravelDTO(

        @NotBlank(message = "La destinazione è un campo obbligatorio!")
        @Size(min = 2, max = 30, message = "La destinazione deve essere tra i 2 e i 30 caratteri!")
        String destination,

        @NotNull(message = "La data è un campo obbligatorio!")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        LocalDate travelDate) {
}
