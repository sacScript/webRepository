package com.example.demo.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import com.example.demo.api.dto.AirportDtos.CreateAirportDTO;
import com.example.demo.api.dto.AirportDtos.UpdateAirportDTO;
import com.example.demo.api.dto.AirportDtos.AirportResponseDTO;
import com.example.demo.domain.entities.Airport;
import com.example.demo.domain.repositories.AirportRepository;
import com.example.demo.exceptions.NotFoundException;
    

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AirportServiceImplTest {

    @Mock
    private AirportRepository repo;

    @InjectMocks
    private AirportServiceImpl service;

    @Test
    void shouldCreateAndReturnResponse() {
        var req = new CreateAirportDTO("AirportTest", "AT");

        when(repo.save(any())).thenAnswer(inv -> {
            Airport a = inv.getArgument(0);
            a.setId(1L);
            return a;
        });

        AirportResponseDTO res = service.create(req);

        assertThat(res.id()).isEqualTo(1L);
        assertThat(res.name()).isEqualTo("AirportTest");
        assertThat(res.code()).isEqualTo("AT");
        verify(repo).save(any(Airport.class));
    }

    @Test
    void shouldGetExistingAirport() {
        var airport = new Airport();
        airport.setId(5L);
        airport.setName("ExistingAirport");
        airport.setCode("EX");

        when(repo.findById(5L)).thenReturn(Optional.of(airport));

        AirportResponseDTO res = service.get(5L);

        assertThat(res.id()).isEqualTo(5L);
        assertThat(res.name()).isEqualTo("ExistingAirport");
        assertThat(res.code()).isEqualTo("EX");
    }

    @Test
    void shouldThrowWhenAirportNotFound() {
        when(repo.findById(99L)).thenReturn(Optional.empty());

        org.assertj.core.api.Assertions.assertThatThrownBy(() -> service.get(99L))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("Airport 99 not found");
    }

    @Test
    void shouldUpdateAirport() {
        var airport = new Airport();
        airport.setId(7L);
        airport.setName("OldName");
        airport.setCode("ON");

        when(repo.findById(7L)).thenReturn(Optional.of(airport));
        when(repo.save(any())).thenAnswer(inv -> inv.getArgument(0));

        var updateDto = new UpdateAirportDTO("NewName", "NN");
        AirportResponseDTO res = service.update(7L, updateDto);

        assertThat(res.id()).isEqualTo(7L);
        assertThat(res.name()).isEqualTo("NewName");
        assertThat(res.code()).isEqualTo("NN");
    }

    @Test
    void shouldListAllAirports() {
        var airports = List.of(
                new Airport() {{ setId(1L); setName("A1"); setCode("AA"); }},
                new Airport() {{ setId(2L); setName("A2"); setCode("AB"); }}
        );

        when(repo.findAll()).thenReturn(airports);

        List<AirportResponseDTO> res = service.list();

        assertThat(res).hasSize(2);
        assertThat(res.get(0).name()).isEqualTo("A1");
        assertThat(res.get(0).code()).isEqualTo("AA");
        assertThat(res.get(1).name()).isEqualTo("A2");
        assertThat(res.get(1).code()).isEqualTo("AB");
    }

    @Test
    void shouldDeleteAirport() {
        service.delete(3L);
        verify(repo).deleteById(3L);
    }
}
