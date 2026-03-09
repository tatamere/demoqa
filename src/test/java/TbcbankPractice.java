import com.microsoft.playwright.*;
import com.microsoft.playwright.options.AriaRole;
import org.junit.jupiter.api.*;

import java.net.URI;
import java.util.List;
import java.util.regex.Pattern;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TbcbankPractice {

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
    // 1. Scenario 4: Offers List-Empty Result Validation (Mastercard filter)
    @Test
    void offersListEmptyResultValidation() {
        page = browser.newPage();
        page.navigate("https://tbcbank.ge/ka/offers/all-offers");
        page.waitForTimeout(5000);
        assertTrue(page.locator("app-marketing-filters").isVisible());

        Locator partnerOffer = page.locator(
                "div.filter-item__label:has-text('პარტნიორების შეთავაზება')");

        partnerOffer.click();

        Locator mastercard = page.locator(
                "div.filter-item__label:has-text('მასტერქარდი')");

        mastercard.click();

        assertTrue(page.locator(".marketing__cards-list > a").count() == 0);
        int offerCount = page.locator(".marketing__cards-list > a").count();

        assertEquals(0, offerCount);

        page.waitForTimeout(5000);
        page.locator("app-marketing-filter-group:has-text('შეთავაზების ტიპი') button")
                .click();
        page.waitForTimeout(5000);
        assertTrue(page.locator(".marketing__cards-list > a").count() > 0);

    }

    // 2. Scenario 5: Loans – Apply Button Redirect to TBC Credit
    @Test
    void ApplyButtonRedirecttoTBCCredit() {
        page = browser.newPage();
        page.navigate("https://tbcbank.ge/ka");
        //page.waitForTimeout(5000);


        page.getByRole(AriaRole.BANNER)
                .getByRole(AriaRole.LINK,
                        new Locator.GetByRoleOptions().setName("ჩემთვის"))
                .hover();

        page.getByRole(AriaRole.BUTTON,
                new Page.GetByRoleOptions().setName("სამომხმარებლო")).click();

        Locator heading = page.getByRole(AriaRole.HEADING);
       assertThat(heading.first()).isVisible();
        page.waitForTimeout(5000);

      page.getByRole(AriaRole.BUTTON,
                new Page.GetByRoleOptions().setName("პირობები")).first().click();

       Locator applyButton = page.getByRole(AriaRole.BUTTON,
                new Page.GetByRoleOptions().setName("სესხის მოთხოვნა"));

       assertThat(applyButton).isVisible();
        assertThat(applyButton).isEnabled();


        Page newPage = page.waitForPopup(applyButton::click);
        String currentUrl = newPage.url();
        String host = URI.create(currentUrl).getHost();
        System.out.println("Current host: " + host);
        assert host.contains("tbccredit") || host.equals("tbccredit.ge");

       assertThat(page.locator("form")).isVisible();
    }

    }
