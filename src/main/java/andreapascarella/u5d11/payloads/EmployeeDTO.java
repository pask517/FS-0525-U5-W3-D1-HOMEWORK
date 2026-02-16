package andreapascarella.u5d11.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record EmployeeDTO(
        @NotBlank(message = "Lo username è un campo obbligatorio!")
        @Size(min = 2, max = 30, message = "Lo username deve essere tra i 2 e i 30 caratteri!")
        String username,

        @NotBlank(message = "Il nome proprio è un campo obbligatorio!")
        @Size(min = 2, max = 20, message = "Il nome proprio deve essere tra i 2 e i 20 caratteri!")
        String name,

        @NotBlank(message = "Il cognome è un campo obbligatorio!")
        @Size(min = 2, max = 20, message = "Il cognome deve essere tra i 2 e i 20 caratteri!")
        String surname,

        @NotBlank(message = "L'email è obbligatoria!")
        @Email(message = "L'indirizzo email inserito non è nel formato corretto!")
        String email,

        @NotBlank(message = "La password è obbligatoria")
        @Size(min = 4, message = "La password deve avere almeno 4 caratteri")
        @Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{4,}$", message = "La password deve contenere una maiuscola, una minuscola ecc ecc ...")
        String password) {
}
