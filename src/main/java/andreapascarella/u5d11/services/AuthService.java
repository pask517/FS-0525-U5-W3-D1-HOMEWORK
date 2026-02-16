package andreapascarella.u5d11.services;

import andreapascarella.u5d11.entities.Employee;
import andreapascarella.u5d11.exceptions.UnauthorizedException;
import andreapascarella.u5d11.payloads.LoginDTO;
import andreapascarella.u5d11.security.JWTTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final EmployeesService employeesService;
    private final JWTTools jwtTools;

    @Autowired
    public AuthService(EmployeesService employeesService, JWTTools jwtTools) {

        this.employeesService = employeesService;
        this.jwtTools = jwtTools;
    }

    public String checkCredentialsAndGenerateToken(LoginDTO body) {
        // 1. Controllo credenziali
        // 1.1 Controllo se esiste un utente con quell'email
        Employee found = this.employeesService.findByEmail(body.email());

        // 1.2 Se esiste controllo se la sua password (quella nel DB) è uguale a quella nel body
        // TODO: migliorare gestione password
        if (found.getPassword().equals(body.password())) {
            // 2. Se credenziali OK
            // 2.1 Genero token
            String accessToken = jwtTools.generateToken(found);

            // 2.2 Ritorno token
            return accessToken;

        } else {
            // 3. Se credenziali non ok --> 401 Unauthorized
            throw new UnauthorizedException("Credenziali errate!");
        }


    }
}
