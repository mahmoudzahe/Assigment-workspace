package main.najah.test;

import main.najah.code.UserService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static org.junit.jupiter.api.Assertions.*;
import java.time.Duration;

@DisplayName("UserService Tests")
public class UserServiceTest {
    private UserService userService;

    @BeforeEach
    void setUp() {
        userService = new UserService();
    }

    @Test
    @DisplayName("Test email validation with valid emails")
    void testValidEmail() {
        assertAll("Valid email checks",
            () -> assertTrue(userService.isValidEmail("user@example.com")),
            () -> assertTrue(userService.isValidEmail("test.email@domain.co"))
        );
    }

    @Test
    @DisplayName("Test email validation with invalid emails")
    void testInvalidEmail() {
        assertAll("Invalid email checks",
            () -> assertFalse(userService.isValidEmail(null)),
            () -> assertFalse(userService.isValidEmail("invalid-email")),
            () -> assertFalse(userService.isValidEmail("no-dot@domain"))
        );
    }

    @Test
    @DisplayName("Test authentication with valid credentials")
    void testAuthenticateValid() {
        assertTrue(userService.authenticate("admin", "1234"));
    }

    @Test
    @DisplayName("Test authentication with invalid credentials")
    void testAuthenticateInvalid() {
        assertAll("Invalid authentication checks",
            () -> assertFalse(userService.authenticate("user", "1234")),
            () -> assertFalse(userService.authenticate("admin", "wrong")),
            () -> assertFalse(userService.authenticate("user", "wrong"))
        );
    }

    @ParameterizedTest
    @CsvSource({
        "test@domain.com, true",
        "invalid.email, false",
        "another@domain, false"
    })
    @DisplayName("Test email validation with parameterized inputs")
    void testEmailValidationParameterized(String email, boolean expected) {
        assertEquals(expected, userService.isValidEmail(email));
    }

    @Test
    @Timeout(1)
    @DisplayName("Test timeout for authentication")
    void testAuthenticateTimeout() {
        assertTimeout(Duration.ofSeconds(1), () -> userService.authenticate("admin", "1234"));
        assertTrue(userService.authenticate("admin", "1234"));
    }

    @Test
    @Disabled("Fails because it should return true, not false fix by change it to true")
    @DisplayName("Intentionally failing test for authentication")
    void testAuthenticateInvalidResult() {
        assertFalse(userService.authenticate("admin", "1234")); 
    }
}