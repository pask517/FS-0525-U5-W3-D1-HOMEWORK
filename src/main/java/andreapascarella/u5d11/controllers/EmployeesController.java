package andreapascarella.u5d11.controllers;

import andreapascarella.u5d11.entities.Employee;
import andreapascarella.u5d11.exceptions.ValidationException;
import andreapascarella.u5d11.payloads.EmployeeDTO;
import andreapascarella.u5d11.services.EmployeesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/employees")
public class EmployeesController {

    private final EmployeesService employeesService;

    @Autowired
    public EmployeesController(EmployeesService employeesService) {
        this.employeesService = employeesService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Employee createEmployee(@RequestBody @Validated EmployeeDTO payload, BindingResult validation) {
        if (validation.hasErrors()) {
            List<String> errorsList = validation.getFieldErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .toList();
            throw new ValidationException(errorsList);
        } else {
            return this.employeesService.saveEmployee(payload);
        }
    }

    @GetMapping
    public Page<Employee> findAll(@RequestParam(defaultValue = "0") int page,
                                  @RequestParam(defaultValue = "10") int size,
                                  @RequestParam(defaultValue = "surname") String orderBy,
                                  @RequestParam(defaultValue = "asc") String sortCriteria) {

        return this.employeesService.findAll(page, size, orderBy, sortCriteria);
    }

    @GetMapping("/{employeeId}")
    public Employee findById(@PathVariable UUID employeeId) {
        return this.employeesService.findById(employeeId);
    }

    @PatchMapping("/{employeeId}/avatar")
    public String uploadImage(@RequestParam("profile_picture") MultipartFile file, @PathVariable UUID employeeId) {

        return this.employeesService.uploadAvatar(file);
    }
}
