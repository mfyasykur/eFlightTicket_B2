package org.binar.eflightticket_b2.service.impl;

import org.binar.eflightticket_b2.dto.AircraftDTO;
import org.binar.eflightticket_b2.entity.Aircraft;
import org.binar.eflightticket_b2.exception.ResourceNotFoundException;
import org.binar.eflightticket_b2.repository.AircraftRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AircraftServiceImplTest {

    @Mock
    AircraftRepository aircraftRepository;

    @Mock
    ModelMapper modelMapper;

    @InjectMocks
    private AircraftServiceImpl aircraftService;

    public static Aircraft aircraftData() {

        Aircraft aircraft = new Aircraft();
        aircraft.setId(99L);
        aircraft.setManufacture("Boeing");
        aircraft.setManufactureCode("Boeing-99");
        aircraft.setRegisterCode("AN-69");
        aircraft.setSeatCapacity(999);
        aircraft.setBaggageCapacity(77);
        aircraft.setSizeType(Aircraft.SizeType.LARGE);

        return aircraft;
    }

    @Test
    @DisplayName("Add Aircraft SUCCESS")
    void addAircraftSuccess() {

        Aircraft aircraft = aircraftData();

        when(aircraftRepository.save(any(Aircraft.class))).thenReturn(aircraft);

        aircraftService.addAircraft(aircraft);

        verify(aircraftRepository).save(aircraft);
    }

    @Test
    @DisplayName("Update Aircraft By Id SUCCESS")
    void updateAircraftSuccess() {

        Aircraft aircraft = aircraftData();

        when(aircraftRepository.findById(anyLong())).thenReturn(Optional.of(aircraft));
        when(aircraftRepository.save(any(Aircraft.class))).thenReturn(aircraft);

        Aircraft updatedAircraft = aircraftService.updateAircraft(99L, aircraft);
        assertEquals(aircraft.getId(), updatedAircraft.getId());
    }

    @Test
    @DisplayName("Update Aircraft By Id FAILED ID NOT FOUND")
    void updateAircraftByIdNotFound() {

        Aircraft aircraft = new Aircraft();

        when(aircraftRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> aircraftService.updateAircraft(1L, aircraft))
                .isInstanceOf(ResourceNotFoundException.class);

        verify(aircraftRepository).findById(anyLong());
    }

    @Test
    @DisplayName("Delete Aircraft By Id SUCCESS")
    void deleteAircraftSuccess() {

        Aircraft aircraft = aircraftData();

        when(aircraftRepository.findById(anyLong())).thenReturn(Optional.of(aircraft));

        Aircraft deletedAircraft = aircraftService.deleteAircraft(anyLong());

        assertEquals(aircraft.getId(), deletedAircraft.getId());

        verify(aircraftRepository).delete(aircraft);
    }

    @Test
    @DisplayName("Delete Aircraft By Id FAILED ID NOT FOUND")
    void deleteAircraftByIdNotFound() {

        when(aircraftRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> aircraftService.deleteAircraft(1L))
                .isInstanceOf(ResourceNotFoundException.class);

        verify(aircraftRepository).findById(1L);
    }

    @Test
    @DisplayName("Get All Aircraft SUCCESS")
    void getAllAircraftSuccess() {

        List<Aircraft> aircraftList = List.of(aircraftData(), aircraftData());

        when(aircraftRepository.findAll()).thenReturn(aircraftList);

        aircraftService.getAllAircraft();

        verify(aircraftRepository).findAll();
    }

    @Test
    @DisplayName("Get Aircraft by Id SUCCESS")
    void getAircraftByIdSuccess() {

        Aircraft aircraft = aircraftData();

        when(aircraftRepository.findById(anyLong())).thenReturn(Optional.of(aircraft));

        Aircraft retrievedAircraft = aircraftService.getAircraftById(99L);

        assertEquals(aircraft.getManufacture(), retrievedAircraft.getManufacture());

        verify(aircraftRepository).findById(99L);
    }

    @Test
    @DisplayName("Get Aircraft By Id FAILED ID NOT FOUND")
    void getAircraftByIdNotFound() {

        when(aircraftRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> aircraftService.getAircraftById(1L))
                .isInstanceOf(ResourceNotFoundException.class);

        verify(aircraftRepository).findById(anyLong());
    }

    @Test
    @DisplayName("Map Entity to DTO SUCCESS")
    void mapToDTOSuccess() {

        AircraftDTO aircraftDTO = new AircraftDTO();
        aircraftDTO.setId(99L);
        aircraftDTO.setManufacture("Boeing");
        aircraftDTO.setManufactureCode("Boeing-99");
        aircraftDTO.setRegisterCode("AN-69");
        aircraftDTO.setSeatCapacity(999);
        aircraftDTO.setBaggageCapacity(77);
        aircraftDTO.setSizeType(Aircraft.SizeType.LARGE);

        Aircraft aircraft = new Aircraft();

        when(aircraftService.mapToDto(aircraft)).thenReturn(aircraftDTO);

        AircraftDTO dtoResult = aircraftService.mapToDto(aircraft);

        assertEquals(aircraftDTO.getBaggageCapacity(), dtoResult.getBaggageCapacity());
    }

    @Test
    @DisplayName("Map DTO to Entity SUCCESS")
    void mapToEntitySuccess() {

        Aircraft aircraft = aircraftData();
        AircraftDTO aircraftDTO = new AircraftDTO();

        when(aircraftService.mapToEntity(aircraftDTO)).thenReturn(aircraft);

        Aircraft entityResult = aircraftService.mapToEntity(aircraftDTO);

        assertEquals(entityResult.getBaggageCapacity(), aircraft.getBaggageCapacity());
    }
}
