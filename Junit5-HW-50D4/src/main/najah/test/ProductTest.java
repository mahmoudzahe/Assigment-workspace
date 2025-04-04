package main.najah.test;

import main.najah.code.Product;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static org.junit.jupiter.api.Assertions.*;
import java.time.Duration;

@DisplayName("Product Tests")
public class ProductTest {
    private Product product;

    @BeforeEach
    void setUp() {
        product = new Product("Laptop", 1000.0);
    }

    @Test
    @DisplayName("Test product creation with valid inputs")
    void testProductCreationValid() {
        assertAll("Product creation checks",
            () -> assertEquals("Laptop", product.getName(), "Name should be Laptop"),
            () -> assertEquals(1000.0, product.getPrice(), "Price should be 1000.0"),
            () -> assertEquals(0.0, product.getDiscount(), "Initial discount should be 0.0")
        );
    }

    @Test
    @DisplayName("Test product creation with invalid price")
    void testProductCreationInvalidPrice() {
        assertThrows(IllegalArgumentException.class, () -> new Product("Invalid", -50.0), "Negative price should throw IllegalArgumentException");
    }

    @Test
    @DisplayName("Test apply discount with valid percentage")
    void testApplyDiscountValid() {
        product.applyDiscount(20.0);
        assertAll("Discount application checks",
            () -> assertEquals(20.0, product.getDiscount(), "Discount should be 20.0"),
            () -> assertEquals(800.0, product.getFinalPrice(), "Final price after 20% discount should be 800.0")
        );
    }

    @Test
    @DisplayName("Test apply discount with invalid percentage")
    void testApplyDiscountInvalid() {
        assertThrows(IllegalArgumentException.class, () -> product.applyDiscount(75.0), "Discount percentage > 50 should throw IllegalArgumentException");
        assertThrows(IllegalArgumentException.class, () -> product.applyDiscount(-10.0), "Negative discount percentage should throw IllegalArgumentException");
    }

    @ParameterizedTest
    @CsvSource({
        "Phone, 500.0",
        "Tablet, 300.0",
        "Monitor, 200.0"
    })
    @DisplayName("Test product creation with parameterized inputs")
    void testProductCreationParameterized(String name, double price) {
        Product p = new Product(name, price);
        assertAll("Parameterized product creation",
            () -> assertEquals(name, p.getName()),
            () -> assertEquals(price, p.getPrice())
        );
    }

    @Test
    @Timeout(1)
    @DisplayName("Test timeout for applying discount")
    void testApplyDiscountTimeout() {
        assertTimeout(Duration.ofSeconds(1), () -> product.applyDiscount(10.0));
        assertEquals(900.0, product.getFinalPrice(), "Final price after 10% discount should be 900.0");
    }

    @Test
    @Disabled("Fails because 1000 - (20% of 1000) = 800, not 700 fix by changing 700.0 to 800.0")
    @DisplayName("Intentionally failing test for final price after discount")
    void testFinalPriceInvalid() {
        product.applyDiscount(20.0);
        assertEquals(700.0, product.getFinalPrice()); 
    }
}