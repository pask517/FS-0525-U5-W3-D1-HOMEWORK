package andreapascarella.u5d11.services;

import andreapascarella.u5d11.entities.Employee;
import andreapascarella.u5d11.exceptions.BadRequestException;
import andreapascarella.u5d11.exceptions.NotFoundException;
import andreapascarella.u5d11.payloads.EmployeeDTO;
import andreapascarella.u5d11.repositories.EmployeesRepository;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;


@Service
@Slf4j
public class EmployeesService {

    private final EmployeesRepository employeesRepository;
    private final Cloudinary cloudinaryUploader;

    @Autowired
    public EmployeesService(EmployeesRepository employeesRepository, Cloudinary cloudinaryUploader) {
        this.employeesRepository = employeesRepository;
        this.cloudinaryUploader = cloudinaryUploader;
    }

    public Employee saveEmployee(EmployeeDTO payload) {
        if (this.employeesRepository.existsByEmail(payload.email())) {
            throw new BadRequestException("L' email " + payload.email() + " é giá in uso!");
        }

        if (this.employeesRepository.existsByUsername(payload.username())) {
            throw new BadRequestException("L' username " + payload.username() + " é giá in uso!");
        }

        Employee newEmployee = new Employee(payload.username(), payload.name(), payload.surname(), payload.email(), payload.password());

        Employee savedEmployee = this.employeesRepository.save(newEmployee);

        log.info("L'utente con id " + savedEmployee.getEmployeeId() + " è stato salvato correttamente!");

        return savedEmployee;
    }

    public Page<Employee> findAll(int page, int size, String orderBy, String sortCriteria) {
        if (size > 100 || size < 0) size = 10;
        if (page < 0) page = 0;
        Pageable pageable = PageRequest.of(page, size,
                sortCriteria.equals("desc") ? Sort.by(orderBy).descending() : Sort.by(orderBy));
        return this.employeesRepository.findAll(pageable);
    }

    public Employee findById(UUID employeeId) {
        return this.employeesRepository.findById(employeeId)
                .orElseThrow(() -> new NotFoundException(employeeId));
    }

    public String uploadAvatar(MultipartFile file) {
        try {
            Map result = cloudinaryUploader.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());

            String imageUrl = (String) result.get("secure_url");

            return imageUrl;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Employee findByEmail(String email) {
        return this.employeesRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("L'utente con email " + email + " non è stato trovato!"));
    }
}
