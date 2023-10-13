package tests;

import base.TestBase;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.HomePage;
import pages.ProductPage;
import pages.ProductListPage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Random;

import static com.codeborne.selenide.Selenide.$$;

public class Tests extends TestBase {
    HomePage homePage = new HomePage();
    ProductListPage productListPage = new ProductListPage();
    ProductPage productPage = new ProductPage();

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

            softAssert.assertTrue(productPage.isCurrentItemPriceExists(), "Product current price exists:");
            softAssert.assertTrue(productPage.isOldItemPriceExists(), "Product old price exists:");

            itemPageCurrentPrice = productPage.getItemRegularPrice();
            itemPageOldPrice = productPage.getItemOldPrice();

            softAssert.assertEquals(productListPageCurrentPrice, itemPageCurrentPrice, "Product current prices are equal:");
            softAssert.assertEquals(productListPageOldPrice, itemPageOldPrice, "Product old prices are equal:");

            Selenide.back();
        }
        softAssert.assertAll();
    }

    @Test
    public void test2() {
        boolean isActionTickerPresent = false;

        homePage
                .openCatalogMenu()
                .focusOnCategory("dacha-sad-remont")
                .clickOnSubcategory("Perforators");

        while (!isActionTickerPresent) {
            if (productListPage.isActionStickerPresentOnThePage()) {
                isActionTickerPresent = true;
            } else productListPage.clickNextPaginationItem();
        }
        Assert.assertTrue(productListPage.isActionStickerPresentOnThePage());
    }

    @Test
    public void test3() {
        homePage
                .openCatalogMenu()
                .focusOnCategory("dacha-sad-remont")
                .clickOnSubcategory("Grinders");

        productListPage.clickLoad30MoreItemsButtonNthTimes(3);

        for (int i = 0; i <= productListPage.getItemsQty(); i++) {
            if (productListPage.isNthProductHasPbBankSticker(i)) {
                System.out.println(productListPage.getNthItemName(i));
            }
        }
    }

    @Test
    public void test4() {
        LinkedHashSet<String> productNames = new LinkedHashSet<>();
        LinkedHashSet<Float> regularPrices = new LinkedHashSet<>();
        LinkedHashSet<Float> oldPrices = new LinkedHashSet<>();
        Random random = new Random();
        int i = 0;

        homePage
                .openCatalogMenu()
                .focusOnCategory("dacha-sad-remont")
                .clickOnSubcategory("Screwdrivers");

        while (i < 10) {
            int temp = 1;
            int discountItemsQty = productListPage.getItemsWithDiscountQty();
            int randomDiscountItemNumber = random.nextInt(1, discountItemsQty);

            for (int j = 0; j <= productListPage.getItemsQty(); j++) {

                if (productListPage.getNthItemOldPrice(j) != 0) {
                    productNames.add(productListPage.getNthItemName(j));
                    regularPrices.add(productListPage.getNthItemRegularPrice(j));
                    oldPrices.add(productListPage.getNthItemOldPrice(j));
                    i++;
                }
            }
            productListPage.clickNextPaginationItem();
        }

        String[] names = new String[productNames.size()];
        names = productNames.toArray(names);
        Float[] actualPrices = new Float[regularPrices.size()];
        actualPrices = regularPrices.toArray(actualPrices);
        Float[] old = new Float[oldPrices.size()];
        old = oldPrices.toArray(old);



        for (int j = 0; j < names.length; j++) {
            float discount = (float) (100.0 - (actualPrices[j] * 100.0) / old[j]);
            System.out.println("Name: " + names[j] + "; Actual price: " + actualPrices[j] + "; Old price: " + old[j] + "; Discount: " + discount);
        }
    }
}
