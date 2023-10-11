package tests;

import base.TestBase;
import com.codeborne.selenide.Selenide;
import org.testng.annotations.Test;
import pages.HomePage;

public class Tests extends TestBase {
    HomePage homePage = new HomePage();

    @Test
    public void test1() throws InterruptedException {
        openHomePage();

        homePage.openCatalogMenu();
        homePage.focusOnCategory("dacha-sad-remont");

        Thread.sleep(1000);

        homePage.clickOnSubcategory("Perforators");

        Thread.sleep(1000);
    }
}
