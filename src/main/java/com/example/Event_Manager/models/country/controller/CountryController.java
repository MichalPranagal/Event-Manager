package com.example.Event_Manager.models.country.controller;

import com.example.Event_Manager.models.country.dto.request.CreateCountryDTO;
import com.example.Event_Manager.models.country.dto.request.UpdateCountryDTO;
import com.example.Event_Manager.models.country.dto.response.CountryDTO;
import com.example.Event_Manager.models.country.service.CountryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/countries")
@RequiredArgsConstructor
@Validated
public class CountryController implements CountryApi {

    private final CountryService countryService;

    @Override
    public ResponseEntity<CountryDTO> create(CreateCountryDTO createCountryDTO) {
        return null;
    }

    @Override
    public ResponseEntity<CountryDTO> update(String code, UpdateCountryDTO updateCountryDTO) {
        return null;
    }

    @Override
    @DeleteMapping("/{code}")
    @IsAdmin
    public ResponseEntity<Void> delete(@PathVariable String code) {
        countryService.delete(code);
        return ResponseEntity.noContent().build();
    }

    @Override
    @GetMapping("/{code}")
    public ResponseEntity<CountryDTO> getByCode(@PathVariable String code) {
        return ResponseEntity.ok(countryService.getByCode(code));
    }

    @Override
    @GetMapping
    public ResponseEntity<List<CountryDTO>> getAll() {
        return ResponseEntity.ok(countryService.getAll());
    }
}
