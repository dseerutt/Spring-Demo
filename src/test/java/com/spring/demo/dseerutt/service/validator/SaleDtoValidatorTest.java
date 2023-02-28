package com.spring.demo.dseerutt.service.validator;

import com.spring.demo.dseerutt.dto.item.client.SaleComputerDto;
import com.spring.demo.dseerutt.dto.item.client.SaleDto;
import com.spring.demo.dseerutt.model.exception.SaleNotFoundException;
import com.spring.demo.dseerutt.model.exception.computer.ComputerNotFoundException;
import com.spring.demo.dseerutt.model.exception.computer.EmptyStoreException;
import com.spring.demo.dseerutt.model.exception.server.ValidationException;
import com.spring.demo.dseerutt.model.object.Computer;
import com.spring.demo.dseerutt.model.object.ComputerStore;
import com.spring.demo.dseerutt.model.object.Sale;
import com.spring.demo.dseerutt.repository.ComputerRepository;
import com.spring.demo.dseerutt.repository.SaleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SaleDtoValidatorTest {

    @InjectMocks
    private SaleDtoValidator saleDtoValidator;

    @Mock
    private ComputerRepository computerRepository;
    @Mock
    private SaleRepository saleRepository;

    private SaleDto saleDto;
    private Sale sale;
    private SaleComputerDto saleComputerDto;
    private final static String LONG_FIELD = """
            aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa
            aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa
            aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa
            aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa""";
    private final static int COMPUTER_ID = 1;
    private final static int SALE_ID = 2;
    private final static String COMPUTER_BRAND = "ComputerBrand";
    private final static String COMPUTER_VERSION = "ComputerVersion";
    private final static int DEFAULT_STOCK = 4;
    private Computer computer;
    private ComputerStore computerStore;


    @BeforeEach
    void setup() {
        saleDto = new SaleDto();
        computer = new Computer();
        computer.setId(COMPUTER_ID);
        computerStore = new ComputerStore();
        computer.setComputerStore(computerStore);
        saleComputerDto = new SaleComputerDto();
        saleComputerDto.setSale(sale);
        saleComputerDto.setComputer(computer);
    }

    /**
     * validatePost test with 0 quantity -> ValidationException is thrown
     */
    @Test
    void validatePostQuantityValidationExceptionTest() {
        int quantity = 0;
        saleDto.setQuantity(quantity);
        ValidationException exception = assertThrows(
                ValidationException.class,
                () -> saleDtoValidator.validatePost(saleDto),
                "Should throw ValidationException");
        assertEquals("Sale quantity cannot be " + quantity + " or negative", exception.getReason());
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
    }

    /**
     * validatePost test with too long salesman -> ValidationException is thrown
     */
    @Test
    void validatePostSalesmanExceptionTest() {
        saleDto.setQuantity(2);
        saleDto.setSalesman(LONG_FIELD);

        ValidationException exception = assertThrows(
                ValidationException.class,
                () -> saleDtoValidator.validatePost(saleDto),
                "Should throw ValidationException");
        assertEquals("Salesman is limited to 50 characters", exception.getReason());
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
    }

    /**
     * validatePost test with too long clientname -> ValidationException is thrown
     */
    @Test
    void validatePostClientExceptionTest() {
        saleDto.setQuantity(2);
        saleDto.setClientName(LONG_FIELD);

        ValidationException exception = assertThrows(
                ValidationException.class,
                () -> saleDtoValidator.validatePost(saleDto),
                "Should throw ValidationException");
        assertEquals("Client name is limited to 255 characters", exception.getReason());
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
    }

    /**
     * validatePost test with empty sale date -> ValidationException is thrown
     */
    @Test
    void validatePostEmptyDateExceptionTest() {
        saleDto.setQuantity(2);
        saleDto.setSaleDate(null);

        ValidationException exception = assertThrows(
                ValidationException.class,
                () -> saleDtoValidator.validatePost(saleDto),
                "Should throw ValidationException");
        assertEquals("Sale date cannot be empty", exception.getReason());
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
    }

    /**
     * validatePost test with wrong sale date format -> ValidationException is thrown
     */
    @Test
    void validatePostWrongSaleDateFormatExceptionTest() {
        String saleDate = "eadf";
        saleDto.setQuantity(2);
        saleDto.setSaleDate(saleDate);

        ValidationException exception = assertThrows(
                ValidationException.class,
                () -> saleDtoValidator.validatePost(saleDto),
                "Should throw ValidationException");
        assertEquals("Failed to parse saleDate " + saleDate, exception.getReason());
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
    }

    /**
     * validatePut test with 0 quantity -> ValidationException is thrown
     */
    @Test
    void validatePutQuantityValidationExceptionTest() {
        saleDto.setId(1);
        int quantity = 0;
        saleDto.setQuantity(quantity);
        ValidationException exception = assertThrows(
                ValidationException.class,
                () -> saleDtoValidator.validatePut(saleDto),
                "Should throw ValidationException");
        assertEquals("Sale quantity cannot be " + quantity + " or negative", exception.getReason());
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
    }

    /**
     * validatePut test with too long salesman -> ValidationException is thrown
     */
    @Test
    void validatePutSalesmanExceptionTest() {
        saleDto.setId(1);
        saleDto.setQuantity(2);
        saleDto.setSalesman(LONG_FIELD);

        ValidationException exception = assertThrows(
                ValidationException.class,
                () -> saleDtoValidator.validatePut(saleDto),
                "Should throw ValidationException");
        assertEquals("Salesman is limited to 50 characters", exception.getReason());
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
    }

    /**
     * validatePut test with too long clientname -> ValidationException is thrown
     */
    @Test
    void validatePutClientExceptionTest() {
        saleDto.setId(1);
        saleDto.setQuantity(2);
        saleDto.setClientName(LONG_FIELD);

        ValidationException exception = assertThrows(
                ValidationException.class,
                () -> saleDtoValidator.validatePut(saleDto),
                "Should throw ValidationException");
        assertEquals("Client name is limited to 255 characters", exception.getReason());
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
    }

    /**
     * validatePut test with empty sale date -> ValidationException is thrown
     */
    @Test
    void validatePutEmptyDateExceptionTest() {
        saleDto.setId(1);
        saleDto.setQuantity(2);
        saleDto.setSaleDate(null);

        ValidationException exception = assertThrows(
                ValidationException.class,
                () -> saleDtoValidator.validatePut(saleDto),
                "Should throw ValidationException");
        assertEquals("Sale date cannot be empty", exception.getReason());
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
    }

    /**
     * validatePut test with wrong sale date format -> ValidationException is thrown
     */
    @Test
    void validatePutWrongSaleDateFormatExceptionTest() {
        saleDto.setId(1);
        String saleDate = "eadf";
        saleDto.setQuantity(2);
        saleDto.setSaleDate(saleDate);

        ValidationException exception = assertThrows(
                ValidationException.class,
                () -> saleDtoValidator.validatePut(saleDto),
                "Should throw ValidationException");
        assertEquals("Failed to parse saleDate " + saleDate, exception.getReason());
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
    }

    /**
     * validatePost test with id already set -> ValidationException is thrown
     */
    @Test
    void validatePostIdSetValidationExceptionTest() {
        saleDto.setId(1);
        ValidationException exception = assertThrows(
                ValidationException.class,
                () -> saleDtoValidator.validatePost(saleDto),
                "Should throw ValidationException");
        assertEquals("Id cannot be set when using POST", exception.getReason());
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
    }

    /**
     * validatePost test with ComputerNotFound -> ComputerNotFoundException is thrown
     */
    @Test
    void validatePostComputerNotFoundTest() {
        String computerBrand = "ComputerBrand";
        String computerVersion = "ComputerVersion";
        saleDto.setQuantity(2);
        saleDto.setSaleDate("2023-08-09");
        saleDto.setComputerBrand(computerBrand);
        saleDto.setComputerVersion(computerVersion);
        when(computerRepository.findByBrandAndVersion(computerBrand, computerVersion)).thenReturn(Optional.empty());

        ComputerNotFoundException exception = assertThrows(
                ComputerNotFoundException.class,
                () -> saleDtoValidator.validatePost(saleDto),
                "Should throw ComputerNotFoundException"
        );
        assertEquals("Computer not found with brand " + computerBrand + " and version " + computerVersion, exception.getReason());
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
    }

    /**
     * validatePost test with not enough stock to buy -> throw EmptyStoreException
     */
    @Test
    void validatePostNotEnoughStockTest() {
        String computerBrand = "ComputerBrand";
        String computerVersion = "Computer version";
        saleDto.setQuantity(2);
        saleDto.setSaleDate("2023-08-09");
        saleDto.setComputerBrand(computerBrand);
        saleDto.setComputerVersion(computerVersion);
        computerStore.setStock(0);
        when(computerRepository.findByBrandAndVersion(computerBrand, computerVersion)).thenReturn(Optional.of(computer));

        EmptyStoreException exception = assertThrows(
                EmptyStoreException.class,
                () -> saleDtoValidator.validatePost(saleDto),
                "Should throw EmptyStoreException"
        );
        assertEquals("Not enough stock to buy computer with id " + COMPUTER_ID, exception.getReason());
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
    }

    /**
     * validatePost test with nominal case
     */
    @Test
    void validatePostOkTest() {
        saleDto.setQuantity(2);
        saleDto.setSaleDate("2023-08-09");
        saleDto.setComputerBrand(COMPUTER_BRAND);
        saleDto.setComputerVersion(COMPUTER_VERSION);
        computerStore.setStock(DEFAULT_STOCK);
        when(computerRepository.findByBrandAndVersion(COMPUTER_BRAND, COMPUTER_VERSION)).thenReturn(Optional.of(computer));

        assertEquals(saleComputerDto, saleDtoValidator.validatePost(saleDto));
    }

    /**
     * validatePut with sale id that is 0 -> ValidationException is thrown
     */
    @Test
    void validatePutIdIs0Test() {
        saleDto.setId(0);
        ValidationException exception = assertThrows(
                ValidationException.class,
                () -> saleDtoValidator.validatePut(saleDto),
                "Should throw ValidationException"
        );
        assertEquals("Sale Id cannot be 0", exception.getReason());
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
    }

    /**
     * validatePut with sale not found case -> SaleNotFoundException thrown
     */
    @Test
    void validatePutSaleNotFoundExceptionTest() {
        saleDto.setId(SALE_ID);
        saleDto.setComputerBrand(COMPUTER_BRAND);
        saleDto.setComputerVersion(COMPUTER_VERSION);
        saleDto.setQuantity(2);
        saleDto.setSaleDate("2023-08-09");
        when(computerRepository.findByBrandAndVersion(COMPUTER_BRAND, COMPUTER_VERSION)).thenReturn(Optional.of(computer));
        when(saleRepository.findById(saleDto.getId())).thenReturn(Optional.empty());
        SaleNotFoundException exception = assertThrows(
                SaleNotFoundException.class,
                () -> saleDtoValidator.validatePut(saleDto),
                "Should throw SaleNotFoundException"
        );
        assertEquals("Sale with id " + saleDto.getId() + " was not found", exception.getReason());
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
    }

    /**
     * validatePut with nominal case -> saleComputerDto is initialized
     */
    @Test
    void validatePutOkTest() {
        sale = new Sale();
        saleDto.setId(SALE_ID);
        saleDto.setComputerBrand(COMPUTER_BRAND);
        saleDto.setComputerVersion(COMPUTER_VERSION);
        saleDto.setQuantity(2);
        saleDto.setSaleDate("2023-08-09");
        saleComputerDto.setSale(sale);
        when(computerRepository.findByBrandAndVersion(COMPUTER_BRAND, COMPUTER_VERSION)).thenReturn(Optional.of(computer));
        when(saleRepository.findById(saleDto.getId())).thenReturn(Optional.of(sale));
        assertEquals(saleComputerDto, saleDtoValidator.validatePut(saleDto));
    }

    /**
     * validateDelete with element present case, true is returned
     */
    @Test
    void validateDeleteElementPresentTest() {
        sale = new Sale();
        when(saleRepository.findById(SALE_ID)).thenReturn(Optional.of(sale));
        assertTrue(saleDtoValidator.validateDelete(SALE_ID));
    }

    /**
     * validateDelete with element not present, false is returned
     */
    @Test
    void validateDeleteElementNotPresentTest() {
        when(saleRepository.findById(SALE_ID)).thenReturn(Optional.empty());
        assertFalse(saleDtoValidator.validateDelete(SALE_ID));
    }
}