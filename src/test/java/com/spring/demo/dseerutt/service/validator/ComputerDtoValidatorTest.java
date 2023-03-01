package com.spring.demo.dseerutt.service.validator;

import com.spring.demo.dseerutt.dto.item.client.LightComputerDto;
import com.spring.demo.dseerutt.model.exception.computer.ComputerAlreadyExistsException;
import com.spring.demo.dseerutt.model.exception.computer.ComputerNotFoundException;
import com.spring.demo.dseerutt.model.exception.server.ValidationException;
import com.spring.demo.dseerutt.model.object.Computer;
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
class ComputerDtoValidatorTest {

    @InjectMocks
    private ComputerDtoValidator computerDtoValidator;

    @Mock
    private ComputerRepository computerRepository;

    private LightComputerDto lightComputerDto;
    private Computer computer;
    private final static String LONG_FIELD = """
            aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa
            aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa
            aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa
            aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa""";
    private final static String COMPUTER_BRAND = "ComputerBrand";
    private final static String COMPUTER_VERSION = "ComputerVersion";
    private final static int COMPUTER_ID = 1;


    @BeforeEach
    void setup() {
        computer = new Computer();
        lightComputerDto = new LightComputerDto();
        lightComputerDto.setBrand(COMPUTER_BRAND);
        lightComputerDto.setVersion(COMPUTER_VERSION);
    }

    /**
     * validatePost test with description too long case -> ValidationException is thrown
     */
    @Test
    void validatePostDescriptionTooLongTest() {
        lightComputerDto.setDescription(LONG_FIELD);
        ValidationException exception = assertThrows(
                ValidationException.class,
                () -> computerDtoValidator.validatePost(lightComputerDto),
                "Should throw ValidationException");
        assertEquals("Description is limited to 255 characters", exception.getReason());
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
    }

    /**
     * validatePost test with rounding price -> price is rounded with 2 decimals
     */
    @Test
    void validatePostRoundingPriceTest() {
        lightComputerDto.setPrice(15.55555);
        computerDtoValidator.validatePost(lightComputerDto);
        assertEquals(15.56, lightComputerDto.getPrice());
    }

    /**
     * validatePut test with description too long case -> ValidationException is thrown
     */
    @Test
    void validatePutDescriptionTooLongTest() {
        lightComputerDto.setId(COMPUTER_ID);
        lightComputerDto.setDescription(LONG_FIELD);
        ValidationException exception = assertThrows(
                ValidationException.class,
                () -> computerDtoValidator.validatePut(lightComputerDto),
                "Should throw ValidationException");
        assertEquals("Description is limited to 255 characters", exception.getReason());
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
    }

    /**
     * validatePut test with rounding price -> price is rounded with 2 decimals
     */
    @Test
    void validatePutRoundingPriceTest() {
        when(computerRepository.findById(COMPUTER_ID)).thenReturn(Optional.of(computer));
        lightComputerDto.setId(COMPUTER_ID);
        lightComputerDto.setPrice(15.55555);
        computerDtoValidator.validatePut(lightComputerDto);
        assertEquals(15.56, lightComputerDto.getPrice());
    }

    /**
     * validatePost with nominal case -> no exception is thrown
     */
    @Test
    void validatePostOkTest() {
        when(computerRepository.existsByBrandAndVersion(COMPUTER_BRAND, COMPUTER_VERSION)).thenReturn(false);
        computerDtoValidator.validatePost(lightComputerDto);
    }

    /**
     * validatePost with set id case -> ValidationException is thrown
     */
    @Test
    void validatePostIdSetTest() {
        lightComputerDto.setId(1);
        ValidationException exception = assertThrows(
                ValidationException.class,
                () -> computerDtoValidator.validatePost(lightComputerDto),
                "Should throw ValidationException");
        assertEquals("Id cannot be set when using POST", exception.getReason());
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
    }

    /**
     * validatePost with computerAlreadyExists case -> ComputerAlreadyExistsException is thrown
     */
    @Test
    void validatePostComputerAlreadyExistsExceptionTest() {
        when(computerRepository.existsByBrandAndVersion(COMPUTER_BRAND, COMPUTER_VERSION)).thenReturn(true);
        ComputerAlreadyExistsException exception = assertThrows(
                ComputerAlreadyExistsException.class,
                () -> computerDtoValidator.validatePost(lightComputerDto),
                "Should throw ComputerAlreadyExistsException");
        assertEquals("Computer with brand %s and version %s already exists".formatted(lightComputerDto.getBrand(), lightComputerDto.getVersion()), exception.getReason());
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
    }

    /**
     * validatePut test with id set to 0 case -> ValidationException is thrown
     */
    @Test
    void validatePutIdSet0Test() {
        lightComputerDto.setId(0);
        ValidationException exception = assertThrows(
                ValidationException.class,
                () -> computerDtoValidator.validatePut(lightComputerDto),
                "Should throw ValidationException");
        assertEquals("Computer Id has to be related to an existing computer when using POST", exception.getReason());
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
    }

    /**
     * validatePut test with computer not found case -> ComputerNotFound is thrown
     */
    @Test
    void validatePutComputerNotFoundExceptionTest() {
        lightComputerDto.setId(COMPUTER_ID);
        when(computerRepository.findById(COMPUTER_ID)).thenReturn(Optional.empty());
        ComputerNotFoundException exception = assertThrows(
                ComputerNotFoundException.class,
                () -> computerDtoValidator.validatePut(lightComputerDto),
                "Should throw ComputerNotFoundException");
        assertEquals("Computer with id %s was not found".formatted(COMPUTER_ID), exception.getReason());
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
    }

    /**
     * validatePut test with nominal case
     */
    @Test
    void validatePutOkTest() {
        lightComputerDto.setId(COMPUTER_ID);
        when(computerRepository.findById(COMPUTER_ID)).thenReturn(Optional.of(computer));
        assertEquals(computer, computerDtoValidator.validatePut(lightComputerDto));
    }
}