package main.najah.test;

import main.najah.code.Calculator;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static org.junit.jupiter.api.Assertions.*;
import java.time.Duration;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("Calculator Tests")
public class CalculatorTest {
    private Calculator calc;

    @BeforeAll
    static void initAll() {
        System.out.println("Starting Calculator Tests");
    }

    @BeforeEach
    void setUp() {
        calc = new Calculator();
        System.out.println("Setup complete for test");
    }

    @AfterEach
    void tearDown() {
        System.out.println("Test finished");
    }

    @AfterAll
    static void tearDownAll() {
        System.out.println("All Calculator Tests completed");
    }

    @Test
    @Order(1)
    @DisplayName("Test addition with valid inputs")
    void testAddValid() {
        assertAll("Addition checks",
            () -> assertEquals(5, calc.add(2, 3), "2 + 3 should equal 5"),
            () -> assertEquals(0, calc.add(0, 0), "0 + 0 should equal 0"),
            () -> assertEquals(-1, calc.add(-2, 1), "-2 + 1 should equal -1"),
            () -> assertEquals(10, calc.add(1, 2, 3, 4), "1 + 2 + 3 + 4 should equal 10")
        );
    }

    @Test
    @Order(2)
    @DisplayName("Test division with valid inputs")
    void testDivideValid() {
        assertAll("Division checks",
            () -> assertEquals(5, calc.divide(10, 2), "10 / 2 should equal 5"),
            () -> assertEquals(-2, calc.divide(-4, 2), "-4 / 2 should equal -2")
        );
    }

    @Test
    @Order(3)
    @DisplayName("Test division by zero")
    void testDivideByZero() {
        assertThrows(ArithmeticException.class, () -> calc.divide(10, 0), "Division by zero should throw ArithmeticException");
    }

    @ParameterizedTest
    @CsvSource({
        "5, 120",
        "0, 1",
        "3, 6"
    })
    @Order(4)
    @DisplayName("Test factorial with parameterized inputs")
    void testFactorial(int n, int expected) {
        assertEquals(expected, calc.factorial(n), "Factorial of " + n + " should equal " + expected);
    }

    @Test
    @Order(5)
    @DisplayName("Test factorial with invalid input")
    void testFactorialInvalid() {
        assertThrows(IllegalArgumentException.class, () -> calc.factorial(-1), "Factorial of negative number should throw IllegalArgumentException");
    }

    @Test
    @Order(6)
    @Timeout(1)
    @DisplayName("Test timeout for factorial calculation")
    void testFactorialTimeout() {
        assertTimeout(Duration.ofSeconds(1), () -> calc.factorial(5));
        assertEquals(120, calc.factorial(5), "Factorial of 5 should be 120");
    }

    @Test
    @Order(7)
    @Disabled("Fails because 2+3=5, not 6 fix by changing 6 to 5")
    @DisplayName("Intentionally failing test for addition")
    void testAddInvalid() {
        assertEquals(6, calc.add(2, 3)); 
    }
}