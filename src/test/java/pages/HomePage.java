package pages;

import base.TestBase;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;
import java.util.HashMap;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class HomePage extends TestBase {

    // variables
    private final SelenideElement catalogButton = $("#header_catalog");
    private final ElementsCollection categories = $$(By.xpath("//*[@id=\"nav\"]/div/div"));


    // getters
    public SelenideElement getCatalogButton() {
        return catalogButton;
    }

    public ElementsCollection getCategories() {
        return categories;
    }

    public HashMap<String, String> getPowerToolSubcategoriesLinks() {
        HashMap<String, String> powerToolSubcategoriesLinks = new HashMap<>();
//        powerToolSubcategoriesLinks.put("Drills", "/uk/dreli-i-miksery/");
        powerToolSubcategoriesLinks.put("Drills", "/dreli-i-miksery/");
        powerToolSubcategoriesLinks.put("Perforators", "/perforatory/");
        powerToolSubcategoriesLinks.put("Grinders", "/shlifovalnye-mashiny-bolgarki/");
        powerToolSubcategoriesLinks.put("Screwdrivers", "/shurupoverty-i-elektrootvertki/");
        return powerToolSubcategoriesLinks;
    }

    // navigation
    public HomePage openCatalogMenu() {
        getCatalogButton().click();
        return this;
    }

    public HomePage focusOnCategory(String categoryName) {
        switch (categoryName) {
            case "dacha-sad-remont" -> getCategories().get(8).hover();
            case "test" -> getCategories().get(7).hover();
        }
        return this;
    }

    public void clickOnSubcategory(String subcategoryName) {
        String subcategory = getPowerToolSubcategoriesLinks().get(subcategoryName);
        if (isUkrainianLanguage()) {
            subcategory = "/uk" + subcategory;
        }
        String selector = "div.category-inner > div.category-list > a[href=\"" + subcategory + "\"]";

        $(selector).click();
    }
}
