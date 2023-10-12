package tests;

import base.TestBase;
import com.codeborne.selenide.Selenide;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.HomePage;
import pages.ProductPage;
import pages.ProductListPage;

import java.util.Random;

public class Tests extends TestBase {
    HomePage homePage = new HomePage();
    ProductListPage productListPage = new ProductListPage();
    ProductPage productPage = new ProductPage();

    @Test
    public void test0() throws InterruptedException {
        float productListPageCurrentPrice;
        float productListPageOldPrice;
        float itemPageCurrentPrice;
        float itemPageOldPrice;

        openHomePage();

        homePage.openCatalogMenu();
        homePage.focusOnCategory("dacha-sad-remont");

        Thread.sleep(1000);

        homePage.clickOnSubcategory("Drills");

        Thread.sleep(1000);

//        productListPage.clickNthPaginationItem(2);

//        Thread.sleep(1000);

        productListPageCurrentPrice = productListPage.getNthItemRegularPrice(1);
        productListPageOldPrice = productListPage.getNthItemOldPrice(1);

        System.out.println(productListPage.getItemsQty());

        productListPage.clickNthItemName(1);

        itemPageCurrentPrice = productPage.getItemRegularPrice();
        itemPageOldPrice = productPage.getItemOldPrice();

        System.out.println(productListPageCurrentPrice);
        System.out.println(productListPageOldPrice);
        System.out.println(itemPageCurrentPrice);
        System.out.println(itemPageOldPrice);
    }

    @BeforeTest
    private void initialization() {
        openHomePage();
    }

    @Test
    public void test1() {
        int numberOfProductsToCheck = 3;

        Random random = new Random();
        SoftAssert softAssert = new SoftAssert();
        float productListPageCurrentPrice;
        float productListPageOldPrice;
        float itemPageCurrentPrice;
        float itemPageOldPrice;
        int randomItemNumber;

        homePage
                .openCatalogMenu()
                .focusOnCategory("dacha-sad-remont")
                .clickOnSubcategory("Drills");

        for (int i = 0; i < numberOfProductsToCheck; i++) {
            randomItemNumber = random.nextInt(1, productListPage.getItemsQty() + 1);

            productListPageCurrentPrice = productListPage.getNthItemRegularPrice(randomItemNumber);
            productListPageOldPrice = productListPage.getNthItemOldPrice(randomItemNumber);

            productListPage.clickNthItemName(randomItemNumber);

            softAssert.assertTrue(productPage.isCurrentItemPriceExists(), "Product current price exists");
            softAssert.assertTrue(productPage.isOldItemPriceExists(), "Product old price exists");

            itemPageCurrentPrice = productPage.getItemRegularPrice();
            itemPageOldPrice = productPage.getItemOldPrice();

            softAssert.assertEquals(productListPageCurrentPrice, itemPageCurrentPrice);
            softAssert.assertEquals(productListPageOldPrice, itemPageOldPrice);

            Selenide.back();
        }
        softAssert.assertAll();
    }

    @Test
    public void test2() {
        homePage
                .openCatalogMenu()
                .focusOnCategory("dacha-sad-remont")
                .clickOnSubcategory("Perforators");
    }
}
