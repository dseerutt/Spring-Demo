package com.spring.demo.dseerutt.service.impl;

import com.spring.demo.dseerutt.dto.item.client.ComputerDto;
import com.spring.demo.dseerutt.dto.item.client.ComputerStatusDto;
import com.spring.demo.dseerutt.dto.item.client.LightComputerDto;
import com.spring.demo.dseerutt.dto.item.client.ProvisionDto;
import com.spring.demo.dseerutt.model.exception.computer.ComputerAlreadyExistsException;
import com.spring.demo.dseerutt.model.exception.computer.ComputerNotFoundException;
import com.spring.demo.dseerutt.model.exception.computer.EmptyStoreException;
import com.spring.demo.dseerutt.model.object.Computer;
import com.spring.demo.dseerutt.model.object.ComputerStore;
import com.spring.demo.dseerutt.model.utils.Utils;
import com.spring.demo.dseerutt.repository.ComputerRepository;
import com.spring.demo.dseerutt.service.validator.ComputerDtoValidator;
import com.spring.demo.dseerutt.service.validator.ProviderDtoValidator;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ComputerServiceImplTest {

    @InjectMocks
    private ComputerServiceImpl computerServiceImpl;

    @Mock
    private ComputerRepository computerRepository;
    @Mock
    private ComputerDtoValidator computerDtoValidator;
    @Mock
    private ProviderDtoValidator providerDtoValidator;

    private static final int COMPUTER_ID = 1;
    private static final int COMPUTER_ID2 = 2;
    private final static String COMPUTER_BRAND = "ComputerBrand";
    private final static String COMPUTER_VERSION = "ComputerVersion";
    private final static String COMPUTER_BRAND2 = "ComputerBrand2";
    private final static String COMPUTER_VERSION2 = "ComputerVersion2";
    private final static String SERIAL_NUMBER = "8888-9999-7777-1111";
    private final static String DESCRIPTION = "ComputerDescription";
    private final static int STOCK = 5;
    private final static int QUANTITY = 2;
    private final static double PRICE = 12;
    private ComputerDto computerDto, computerDto2;
    private Computer computer, computer2;
    private List<Computer> computerList;
    private List<ComputerDto> computerDtoList;
    private LightComputerDto lightComputerDto;
    private ComputerStatusDto computerStatusDto;
    private ProvisionDto provisionDto, provisionDto2;

    @BeforeEach
    void setup() {
        computer = initComputer(COMPUTER_ID, COMPUTER_VERSION, COMPUTER_BRAND);
        computerDto = initComputerDto(COMPUTER_ID, COMPUTER_VERSION, COMPUTER_BRAND);
        computer2 = initComputer(COMPUTER_ID2, COMPUTER_VERSION2, COMPUTER_BRAND2);
        computerDto2 = initComputerDto(COMPUTER_ID2, COMPUTER_VERSION2, COMPUTER_BRAND2);
        computerList = new ArrayList<>();
        computerDtoList = new ArrayList<>();
        lightComputerDto = initLightComputerDto(COMPUTER_ID, COMPUTER_VERSION, COMPUTER_BRAND);
        computerStatusDto = new ComputerStatusDto();
        computerStatusDto.setEnabled(true);
        provisionDto = new ProvisionDto();
        provisionDto2 = new ProvisionDto();
        provisionDto.setQuantity(QUANTITY);
    }

    private Computer initComputer(int computerId, String version, String brand) {
        var computer = new Computer();
        computer.setId(computerId);
        computer.setVersion(version);
        computer.setBrand(brand);
        computer.setSerialNumber(SERIAL_NUMBER);
        computer.setDescription(DESCRIPTION);
        var computerStore = new ComputerStore();
        computerStore.setStock(STOCK);
        computer.setComputerStore(computerStore);
        computer.setPrice(PRICE);
        return computer;
    }

    private ComputerDto initComputerDto(int computerId, String version, String brand) {
        var computerDto = new ComputerDto();
        computerDto.setId(computerId);
        computerDto.setVersion(version);
        computerDto.setBrand(brand);
        computerDto.setStock(STOCK);
        computerDto.setPrice(PRICE);
        computerDto.setDescription(DESCRIPTION);
        return computerDto;
    }

    private LightComputerDto initLightComputerDto(int computerId, String version, String brand) {
        var lightComputerDto = new LightComputerDto();
        lightComputerDto.setId(computerId);
        lightComputerDto.setVersion(version);
        lightComputerDto.setBrand(brand);
        lightComputerDto.setDescription(DESCRIPTION);
        lightComputerDto.setPrice(PRICE);
        return lightComputerDto;
    }

    /**
     * getComputer test with not found case - ComputerNotFoundException is thrown
     */
    @Test
    void getComputerNotFoundTest() {
        when(computerRepository.findById(COMPUTER_ID)).thenReturn(Optional.empty());
        ComputerNotFoundException exception = assertThrows(
                ComputerNotFoundException.class,
                () -> computerServiceImpl.getComputer(COMPUTER_ID),
                "Should throw ComputerNotFoundException");
        assertEquals("Computer not found with id %s".formatted(COMPUTER_ID), exception.getReason());
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
    }

    /**
     * getComputer test with nominal case - computer is retrieved
     */
    @Test
    void getComputerOkTest() {
        when(computerRepository.findById(COMPUTER_ID)).thenReturn(Optional.of(computer));
        assertEquals(computerDto, computerServiceImpl.getComputer(COMPUTER_ID));
    }

    /**
     * getAllComputers test with empty list case - empty computerDto list is sent
     */
    @Test
    void getAllComputersEmptyListTest() {
        when(computerRepository.findAll()).thenReturn(computerList);
        assertEquals(computerDtoList, computerServiceImpl.getAllComputers());
    }

    /**
     * getAllComputers test with non-empty list case - empty computerDto list is sent
     */
    @Test
    void getAllComputersNonEmptyListTest() {
        computerList.add(computer);
        computerList.add(computer2);
        computerDtoList.add(computerDto);
        computerDtoList.add(computerDto2);
        when(computerRepository.findAll()).thenReturn(computerList);
        assertEquals(computerDtoList, computerServiceImpl.getAllComputers());
    }

    /**
     * addComputer test with nominal case - returns a computerDto
     * Wrapped UUID generation within a static method to be able to test it
     */
    @Test
    void addComputerOkTest() {
        try (MockedStatic<Utils> utilities = Mockito.mockStatic(Utils.class)) {
            utilities.when(Utils::getRandomUUIDString).thenReturn(SERIAL_NUMBER);
            doNothing().when(computerDtoValidator).validatePost(lightComputerDto);
            computer2 = initComputer(COMPUTER_ID, COMPUTER_VERSION, COMPUTER_BRAND);
            computer2.setComputerStore(null);
            when(computerRepository.save(computer2)).thenReturn(computer);
            assertEquals(computerDto, computerServiceImpl.addComputer(lightComputerDto));
        }

    }

    /**
     * addComputer test with validation error case - throws a ComputerNotFound validation
     */
    @Test
    void addComputerComputerNotFoundTest() {
        doThrow(new ComputerNotFoundException(StringUtils.EMPTY)).when(computerDtoValidator).validatePost(lightComputerDto);
        ComputerNotFoundException exception = assertThrows(
                ComputerNotFoundException.class,
                () -> computerServiceImpl.addComputer(lightComputerDto),
                "Should throw ComputerNotFoundException");
        assertEquals(StringUtils.EMPTY, exception.getReason());
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());

    }

    /**
     * updateComputer test with validation error -> throws ComputerAlreadyExistsException
     */
    @Test
    void updateComputerComputerAlreadyExistsExceptionTest() {
        when(computerDtoValidator.validatePut(lightComputerDto)).thenThrow(new ComputerAlreadyExistsException(StringUtils.EMPTY));
        ComputerAlreadyExistsException exception = assertThrows(
                ComputerAlreadyExistsException.class,
                () -> computerServiceImpl.updateComputer(lightComputerDto),
                "Should throw ComputerAlreadyExistsException");
        assertEquals(StringUtils.EMPTY, exception.getReason());
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
    }

    /**
     * updateComputer test with nominal case ComputerAlreadyExistsException
     */
    @Test
    void updateComputerOkTest() {
        when(computerDtoValidator.validatePut(lightComputerDto)).thenReturn(computer);
        when(computerRepository.save(computer)).thenReturn(computer);
        assertEquals(computerDto, computerServiceImpl.updateComputer(lightComputerDto));
    }

    /**
     * provisionComputer test with ComputerNotFoundException validation case
     */
    @Test
    void provisionComputerComputerNotFoundExceptionTest() {
        when(providerDtoValidator.validateProvision(provisionDto)).thenThrow(new ComputerNotFoundException(StringUtils.EMPTY));
        ComputerNotFoundException exception = assertThrows(
                ComputerNotFoundException.class,
                () -> computerServiceImpl.provisionComputer(provisionDto),
                "Should throw ComputerNotFoundException");
        assertEquals(StringUtils.EMPTY, exception.getReason());
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
    }

    /**
     * provisionComputer test with nominal case -> quantity is updated
     */
    @Test
    void provisionComputerOkTest() {
        when(providerDtoValidator.validateProvision(provisionDto)).thenReturn(computer);
        when(computerRepository.save(computer)).thenReturn(computer);
        provisionDto2.setId(COMPUTER_ID);
        provisionDto2.setQuantity(STOCK + provisionDto.getQuantity());
        provisionDto2.setVersion(COMPUTER_VERSION);
        provisionDto2.setBrand(COMPUTER_BRAND);
        assertEquals(provisionDto2, computerServiceImpl.provisionComputer(provisionDto));
        assertEquals(LocalDate.now(), computer.getComputerStore().getLastProvisionDate());
    }

    /**
     * deprovisionComputer test with ComputerNotFoundException validation case
     */
    @Test
    void deprovisionComputerComputerNotFoundExceptionTest() {
        when(providerDtoValidator.validateDeprovision(provisionDto)).thenThrow(new ComputerNotFoundException(StringUtils.EMPTY));
        ComputerNotFoundException exception = assertThrows(
                ComputerNotFoundException.class,
                () -> computerServiceImpl.deprovisionComputer(provisionDto),
                "Should throw ComputerNotFoundException");
        assertEquals(StringUtils.EMPTY, exception.getReason());
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
    }

    /**
     * deprovisionComputer test with EmptyStoreException validation case
     */
    @Test
    void deprovisionComputeEmptyStoreExceptionTest() {
        when(providerDtoValidator.validateDeprovision(provisionDto)).thenThrow(new EmptyStoreException(StringUtils.EMPTY));
        EmptyStoreException exception = assertThrows(
                EmptyStoreException.class,
                () -> computerServiceImpl.deprovisionComputer(provisionDto),
                "Should throw EmptyStoreException");
        assertEquals(StringUtils.EMPTY, exception.getReason());
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
    }

    /**
     * deprovisionComputer test with nominal case -> quantity is updated
     */
    @Test
    void deprovisionComputerOkTest() {
        when(providerDtoValidator.validateDeprovision(provisionDto)).thenReturn(computer);
        when(computerRepository.save(computer)).thenReturn(computer);
        provisionDto2.setId(COMPUTER_ID);
        provisionDto2.setQuantity(STOCK - provisionDto.getQuantity());
        provisionDto2.setVersion(COMPUTER_VERSION);
        provisionDto2.setBrand(COMPUTER_BRAND);
        assertEquals(provisionDto2, computerServiceImpl.deprovisionComputer(provisionDto));
        assertEquals(LocalDate.now(), computer.getComputerStore().getLastProvisionDate());
    }

    /**
     * updateComputer test with nominal case -> Repository call only
     */
    @Test
    void updateComputerStatusOkTest() {
        doNothing().when(computerRepository).updateComputerStatus(COMPUTER_ID, computerStatusDto.isEnabled());
        computerServiceImpl.updateComputerStatus(computerStatusDto, COMPUTER_ID);
    }
}