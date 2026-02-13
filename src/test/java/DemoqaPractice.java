import com.microsoft.playwright.*;
import org.junit.jupiter.api.*;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class DemoqaPractice {

    static Playwright playwright;
    static Browser browser;
    Page page;

    @BeforeAll
    static void beforeAll() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(
                new BrowserType.LaunchOptions()
                        .setHeadless(true)
                        .setArgs(List.of("--start-maximized"))
        );
    }

    @BeforeEach
    void setUp() {
        page = browser.newPage();
        page.navigate("https://demoqa.com/automation-practice-form");
        // page.waitForTimeout(5000);
    }

    @AfterEach
    void tearDown() {
        page.close();
    }

    @AfterAll
    static void afterAll() {
        browser.close();
        playwright.close();
    }

    // 1. Page Load & Basic Elements
    @Test
    public void pageLoadsAndMainFieldsVisible() {
        assertTrue(page.locator("#firstName").isVisible());
        assertTrue(page.locator("#lastName").isVisible());
        assertTrue(page.locator("#userEmail").isVisible());
        Locator femaleLabel = page.locator("label[for='gender-radio-2']");
        assertTrue(femaleLabel.isVisible());
        assertTrue(page.locator("#userNumber").isVisible());
        assertTrue(page.locator("#dateOfBirthInput").isVisible());
        assertTrue(page.locator("#subjectsInput").isVisible());
        assertTrue(page.getByText("Sports").isVisible());
        assertTrue(page.getByText("Reading").isVisible());
        assertTrue(page.getByText("Music").isVisible());
        assertTrue(page.locator("#uploadPicture").isVisible());
        assertTrue(page.locator("#currentAddress").isVisible());
        assertTrue(page.locator("#state").isVisible());
        assertTrue(page.locator("#city").isVisible());
        assertTrue(page.locator("#submit").isVisible());
        assertTrue(page.locator("#submit").isEnabled());
    }

    // 2. Required Fields Validation
    @Test
    void submitWithoutRequiredFieldsAndVerifyValidation() {

        // Submit on empty fields
        page.locator("#submit").click();

        // 1️⃣ Success modal is invisible
        assertFalse(page.locator(".modal-content").isVisible());

        //  First Name
        boolean firstNameValid = (Boolean) page.locator("#firstName")
                .evaluate("el => el.checkValidity()");
        assertFalse(firstNameValid);

        // Last Name
        boolean lastNameValid = (Boolean) page.locator("#lastName")
                .evaluate("el => el.checkValidity()");
        assertFalse(lastNameValid);

        // Gender
        boolean genderValid = (Boolean) page.locator("input[name='gender']").first()
                .evaluate("el => el.closest('form').checkValidity()");
        assertFalse(genderValid);

        // Mobile Number
        boolean mobileValid = (Boolean) page.locator("#userNumber")
                .evaluate("el => el.checkValidity()");
        assertFalse(mobileValid);
    }

    // 3. Happy Path Submission
    @Test
    void submitFormAndVerifyConfirmationModalData() {
        page.fill("#firstName", "Test");
        page.fill("#lastName", "User");
        page.getByText("Female").click();
        page.fill("#userNumber", "5551234567");

        page.click("#submit");

        Locator modal = page.locator(".modal-content");
        assertTrue(modal.isVisible());
        assertTrue(modal.textContent().contains("Test User"));

    }

    // 4. Mobile Number Validation
    @Test
    void invalidAndValidMobileNumberInputs() {
        page.fill("#firstName", "Test");
        page.fill("#lastName", "User");
        page.getByText("Female").click();

        page.fill("#userNumber", "1");


        page.click("#submit");

        Locator modal = page.locator(".modal-content");
        assertFalse(modal.isVisible());

        page.fill("#userNumber", "5551234567");
        page.click("#submit");
        assertTrue(modal.isVisible());
    }
}

