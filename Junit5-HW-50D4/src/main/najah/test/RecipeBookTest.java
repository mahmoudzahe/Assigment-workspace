package main.najah.test;

import main.najah.code.Recipe;
import main.najah.code.RecipeBook;
import main.najah.code.RecipeException;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static org.junit.jupiter.api.Assertions.*;
import java.time.Duration;

@DisplayName("RecipeBook Tests")
@Execution(ExecutionMode.CONCURRENT)
public class RecipeBookTest {
    private RecipeBook recipeBook;
    private Recipe recipe1;
    private Recipe recipe2;

    @BeforeAll
    static void initAll() {
        System.out.println("Starting RecipeBook tests...");
    }

    @BeforeEach
    void setUp() throws RecipeException {
        recipeBook = new RecipeBook();
        recipe1 = new Recipe();
        recipe1.setName("Coffee");
        recipe1.setPrice("50");
        recipe1.setAmtCoffee("3");
        recipe1.setAmtMilk("1");
        recipe1.setAmtSugar("1");
        recipe1.setAmtChocolate("0");

        recipe2 = new Recipe();
        recipe2.setName("Mocha");
        recipe2.setPrice("60");
        recipe2.setAmtCoffee("2");
        recipe2.setAmtMilk("1");
        recipe2.setAmtSugar("1");
        recipe2.setAmtChocolate("2");
        System.out.println("Setup complete");
    }

    @AfterEach
    void tearDown() {
        System.out.println("Test finished");
    }

    @AfterAll
    static void tearDownAll() {
        System.out.println("All RecipeBook tests completed");
    }

    @Test
    @DisplayName("Test adding recipes successfully")
    void testAddRecipeSuccess() {
        assertTrue(recipeBook.addRecipe(recipe1), "Should add recipe1 successfully");
        assertTrue(recipeBook.addRecipe(recipe2), "Should add recipe2 successfully");
        Recipe[] recipes = recipeBook.getRecipes();
        assertAll("Check added recipes",
            () -> assertEquals("Coffee", recipes[0].getName()),
            () -> assertEquals("Mocha", recipes[1].getName())
        );
    }

    @Test
    @DisplayName("Test adding duplicate recipe")
    void testAddDuplicateRecipe() {
        recipeBook.addRecipe(recipe1);
        assertFalse(recipeBook.addRecipe(recipe1), "Should not add duplicate recipe");
    }

    @Test
    @DisplayName("Test adding recipe when book is full")
    void testAddRecipeWhenFull() throws RecipeException {
        recipeBook.addRecipe(recipe1);
        recipeBook.addRecipe(recipe2);
        Recipe recipe3 = new Recipe();
        recipe3.setName("Latte");
        recipe3.setPrice("55");
        recipe3.setAmtCoffee("2");
        recipe3.setAmtMilk("1");
        recipe3.setAmtSugar("1");
        recipe3.setAmtChocolate("0");

        Recipe recipe4 = new Recipe();
        recipe4.setName("Espresso");
        recipe4.setPrice("45");
        recipe4.setAmtCoffee("3");
        recipe4.setAmtMilk("0");
        recipe4.setAmtSugar("0");
        recipe4.setAmtChocolate("0");

        Recipe recipe5 = new Recipe();
        recipe5.setName("Cappuccino");
        recipe5.setPrice("50");
        recipe5.setAmtCoffee("2");
        recipe5.setAmtMilk("2");
        recipe5.setAmtSugar("1");
        recipe5.setAmtChocolate("0");

        recipeBook.addRecipe(recipe3);
        recipeBook.addRecipe(recipe4);
        assertFalse(recipeBook.addRecipe(recipe5), "Should not add recipe when book is full");
    }

    @Test
    @DisplayName("Test deleting recipe")
    void testDeleteRecipe() {
        recipeBook.addRecipe(recipe1);
        assertEquals("Coffee", recipeBook.deleteRecipe(0), "Should delete recipe at index 0 and return its name");
        assertEquals("", recipeBook.getRecipes()[0].getName(), "Deleted recipe name should be empty");
    }

    @Test
    @DisplayName("Test deleting non-existent recipe")
    void testDeleteNonExistentRecipe() {
        assertNull(recipeBook.deleteRecipe(0), "Should return null when deleting non-existent recipe");
    }

    @Test
    @DisplayName("Test editing recipe")
    void testEditRecipe() {
        recipeBook.addRecipe(recipe1);
        assertEquals("Coffee", recipeBook.editRecipe(0, recipe2), "Should edit recipe at index 0 and return old name");
        assertEquals("", recipeBook.getRecipes()[0].getName(), "Edited recipe name should be empty");
    }

    @Test
    @DisplayName("Test editing non-existent recipe")
    void testEditNonExistentRecipe() {
        assertNull(recipeBook.editRecipe(0, recipe1), "Should return null when editing non-existent recipe");
    }

    @ParameterizedTest
    @CsvSource({
        "0, Coffee",
        "1, Mocha"
    })
    @DisplayName("Test adding and retrieving recipes with parameterized inputs")
    void testAddAndRetrieveRecipeParameterized(int index, String name) {
        assertTrue(recipeBook.addRecipe(recipe1), "Should add recipe1");
        assertTrue(recipeBook.addRecipe(recipe2), "Should add recipe2");
        Recipe[] recipes = recipeBook.getRecipes();
        assertEquals("Coffee", recipes[0].getName(), "Recipe at index 0 should be Coffee");
        assertEquals("Mocha", recipes[1].getName(), "Recipe at index 1 should be Mocha");
        assertEquals(name, recipes[index].getName(), "Recipe at index " + index + " should be " + name);
    }

    @Test
    @Timeout(1)
    @DisplayName("Test timeout for adding recipe")
    void testAddRecipeTimeout() {
        assertTimeout(Duration.ofSeconds(1), () -> recipeBook.addRecipe(recipe1));
        assertEquals("Coffee", recipeBook.getRecipes()[0].getName());
    }

    @Test
    @Disabled("Fails because it should return true, not false fix by change it to true")
    @DisplayName("Intentionally failing test for adding recipe")
    void testAddRecipeInvalid() {
        assertFalse(recipeBook.addRecipe(recipe1));
    }
}