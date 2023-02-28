package com.spring.demo.dseerutt.service.validator;

import com.spring.demo.dseerutt.dto.item.client.ProvisionDto;
import com.spring.demo.dseerutt.model.exception.computer.ComputerNotFoundException;
import com.spring.demo.dseerutt.model.exception.computer.EmptyStoreException;
import com.spring.demo.dseerutt.model.object.Computer;
import com.spring.demo.dseerutt.model.object.ComputerStore;
import com.spring.demo.dseerutt.repository.ComputerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProviderDtoValidatorTest {

    @InjectMocks
    private ProviderDtoValidator providerDtoValidator;

    @Mock
    private ComputerRepository computerRepository;
    private Computer computer;

    private ProvisionDto provisionDto;
    private static final int COMPUTER_ID = 1;

    @BeforeEach
    void setup() {
        provisionDto = new ProvisionDto();
        provisionDto.setQuantity(5);
        computer = new Computer();
        var computerStore = new ComputerStore();
        computerStore.setStock(4);
        computer.setComputerStore(computerStore);
        computer.setId(COMPUTER_ID);
    }

    /**
     * validateProvision test with nominal case id
     * Computer found by repository is returned
     */
    @Test
    void validateProvisionByIdOkTest() {
        int computerId = 4;
        when(computerRepository.findById(computerId)).thenReturn(Optional.of(computer));

        provisionDto.setId(computerId);
        assertEquals(computer, providerDtoValidator.validateProvision(provisionDto));
    }

    /**
     * validateProvision test with computer not found case with id
     * ComputerNotFoundException is thrown
     */
    @Test
    void validateProvisionByIdComputerNotFoundTest() {
        int computerId = 4;
        when(computerRepository.findById(computerId)).thenReturn(Optional.empty());

        provisionDto.setId(computerId);

        ComputerNotFoundException exception = assertThrows(
                ComputerNotFoundException.class,
                () -> providerDtoValidator.validateProvision(provisionDto),
                "Should throw ComputerNotFoundException");
        assertEquals("Computer with id " + computerId + " was not found", exception.getReason());
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
    }

    /**
     * validateProvision test with nominal case brand & version
     * Computer found by repository is returned
     */
    @Test
    void validateProvisionByBrandVersionOkTest() {
        String brand = "brand";
        String version = "version";
        when(computerRepository.findByBrandAndVersion(brand, version)).thenReturn(Optional.of(computer));

        provisionDto.setBrand(brand);
        provisionDto.setVersion(version);
        assertEquals(computer, providerDtoValidator.validateProvision(provisionDto));
    }

    /**
     * validateProvision test with computer not found case with brand and version
     * Computer found by repository is returned
     */
    @Test
    void validateProvisionByBrandVersionComputerNotFoundTest() {
        String brand = "brand";
        String version = "version";
        when(computerRepository.findByBrandAndVersion(brand, version)).thenReturn(Optional.empty());

        provisionDto.setBrand(brand);
        provisionDto.setVersion(version);
        ComputerNotFoundException exception = assertThrows(
                ComputerNotFoundException.class,
                () -> providerDtoValidator.validateProvision(provisionDto),
                "Should throw ComputerNotFoundException");
        assertEquals("Computer with brand " + brand + " and version " + version + " was not found", exception.getReason());
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
    }

    /**
     * validateDeprovision test with nominal case id
     * Computer found by repository is returned
     */
    @Test
    void validateDeprovisionByIdOkTest() {
        int computerId = 4;
        int quantity = 3;
        when(computerRepository.findById(computerId)).thenReturn(Optional.of(computer));

        provisionDto.setId(computerId);
        provisionDto.setQuantity(quantity);
        assertEquals(computer, providerDtoValidator.validateDeprovision(provisionDto));
    }

    /**
     * validateDeprovision test with not enough stock case id
     * EmptyStoreException is thrown
     */
    @Test
    void validateDeprovisionByIdStoreExceptionTest() {
        int computerId = 4;
        int quantity = 10;
        when(computerRepository.findById(computerId)).thenReturn(Optional.of(computer));

        provisionDto.setId(computerId);
        provisionDto.setQuantity(quantity);

        EmptyStoreException exception = assertThrows(
                EmptyStoreException.class,
                () -> providerDtoValidator.validateDeprovision(provisionDto),
                "Should throw ComputerNotFoundException");
        assertEquals("Cannot deprovision computer " + COMPUTER_ID + ", there is not enough stock", exception.getReason());
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
    }


    /**
     * validateDeprovision test with computer not found case with id
     * ComputerNotFoundException is thrown
     */
    @Test
    void validateDeprovisionByIdComputerNotFoundTest() {
        int computerId = 4;
        int quantity = 3;
        when(computerRepository.findById(computerId)).thenReturn(Optional.empty());

        provisionDto.setId(computerId);
        provisionDto.setQuantity(quantity);

        ComputerNotFoundException exception = assertThrows(
                ComputerNotFoundException.class,
                () -> providerDtoValidator.validateDeprovision(provisionDto),
                "Should throw ComputerNotFoundException");
        assertEquals("Computer with id " + computerId + " was not found", exception.getReason());
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
    }

    /**
     * validateDeprovision test with nominal case brand & version
     * Computer found by repository is returned
     */
    @Test
    void validateDeprovisionByBrandVersionOkTest() {
        String brand = "brand";
        String version = "version";
        int quantity = 3;
        when(computerRepository.findByBrandAndVersion(brand, version)).thenReturn(Optional.of(computer));

        provisionDto.setBrand(brand);
        provisionDto.setVersion(version);
        provisionDto.setQuantity(quantity);
        assertEquals(computer, providerDtoValidator.validateDeprovision(provisionDto));
    }

    /**
     * validateDeprovision test with Empty Store case brand & version
     * EmptyStoreException is thrown
     */
    @Test
    void validateDeprovisionByBrandVersionEmptyStoreExceptionTest() {
        String brand = "brand";
        String version = "version";
        int quantity = 10;
        when(computerRepository.findByBrandAndVersion(brand, version)).thenReturn(Optional.of(computer));

        provisionDto.setBrand(brand);
        provisionDto.setVersion(version);
        provisionDto.setQuantity(quantity);
        EmptyStoreException exception = assertThrows(
                EmptyStoreException.class,
                () -> providerDtoValidator.validateDeprovision(provisionDto),
                "Should throw ComputerNotFoundException");
        assertEquals("Cannot deprovision computer " + COMPUTER_ID + ", there is not enough stock", exception.getReason());
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
    }

    /**
     * validateDeprovision test with computer not found case with brand and version
     * Computer found by repository is returned
     */
    @Test
    void validateDeprovisionByBrandVersionComputerNotFoundTest() {
        String brand = "brand";
        String version = "version";
        int quantity = 3;
        when(computerRepository.findByBrandAndVersion(brand, version)).thenReturn(Optional.empty());

        provisionDto.setBrand(brand);
        provisionDto.setVersion(version);
        provisionDto.setQuantity(quantity);
        ComputerNotFoundException exception = assertThrows(
                ComputerNotFoundException.class,
                () -> providerDtoValidator.validateDeprovision(provisionDto),
                "Should throw ComputerNotFoundException");
        assertEquals("Computer with brand " + brand + " and version " + version + " was not found", exception.getReason());
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
    }
}