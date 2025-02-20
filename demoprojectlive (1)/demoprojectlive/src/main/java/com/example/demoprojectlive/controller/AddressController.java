package com.example.demoprojectlive.controller;
import com.example.demoprojectlive.DTO.AddressDTO;
import com.example.demoprojectlive.DTO.AddressValidationDTO;
import com.example.demoprojectlive.service.AddressService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/address")
@Validated
public class AddressController {

    private final AddressService addressService;

    @Autowired
    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @GetMapping("/lookup")
    public ResponseEntity<Object> addressLookup(@Valid @RequestBody AddressDTO addressDTO, BindingResult result) {
        if (result.hasErrors()) {
            StringBuilder errorMessages = new StringBuilder();
            List<String> errorList = result.getAllErrors().stream()
                    .map(objectError -> objectError.getDefaultMessage())
                    .toList();
            errorMessages.append(String.join(", ", errorList));
            return ResponseEntity.badRequest().body("Validation error: " + errorMessages);
        }

        return addressService.addressLookup(addressDTO.getOrgId(), addressDTO.getUserId(), addressDTO.getSearch());
    }

    @GetMapping("/validate")
    public ResponseEntity<Object> validateAddress(@Valid @RequestBody AddressValidationDTO addressValidationDTO, BindingResult result) {
        if (result.hasErrors()) {
            StringBuilder errorMessages = new StringBuilder();
            List<String> errorList = result.getAllErrors().stream()
                    .map(objectError -> objectError.getDefaultMessage())
                    .toList();
            errorMessages.append(String.join(", ", errorList));
            return ResponseEntity.badRequest().body("Validation error: " + errorMessages);
        }

        return addressService.validateAddress(addressValidationDTO.getOrgId(), addressValidationDTO.getUserId(), addressValidationDTO.getStreet());
    }
}
