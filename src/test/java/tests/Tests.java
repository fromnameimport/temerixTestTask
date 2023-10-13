package tests;

import base.TestBase;
import com.codeborne.selenide.Selenide;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.HomePage;
import pages.ProductPage;
import pages.ProductListPage;

import java.util.ArrayList;
import java.util.Random;

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

        Assert.assertTrue(productListPage.isActionStickerPresentOnThePage(), "Action sticker is present on the page");
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
        SoftAssert softAssert = new SoftAssert();
        ArrayList<String> productNames = new ArrayList<>();
        ArrayList<Float> actualPrices = new ArrayList<>();
        ArrayList<Float> oldPrices = new ArrayList<>();
        Random random = new Random();

        int indicatorOfTheAddedItems = 1;

        homePage
                .openCatalogMenu()
                .focusOnCategory("dacha-sad-remont")
                .clickOnSubcategory("Screwdrivers");

        while (indicatorOfTheAddedItems <= 10) {
            int discountItemsQty = productListPage.getItemsWithDiscountQty();
            int randomDiscountItemsQtyToAddOnCurrentPage = 0;

            if (discountItemsQty != 0) {
                randomDiscountItemsQtyToAddOnCurrentPage = random.nextInt(1, discountItemsQty);
            }

            for (int j = 0; j < productListPage.getItemsQty(); j++) {
                if (productListPage.getNthItemOldPrice(j) != 0.0F && randomDiscountItemsQtyToAddOnCurrentPage != 0) {
                    productNames.add(productListPage.getNthItemName(j));
                    actualPrices.add(productListPage.getNthItemRegularPrice(j));
                    oldPrices.add(productListPage.getNthItemOldPrice(j));
                    randomDiscountItemsQtyToAddOnCurrentPage--;
                    indicatorOfTheAddedItems++;
                }
            }
            productListPage.clickNextPaginationItem();
        }

        for (int j = 0; j < productNames.size(); j++) {
            Float oldPrice = oldPrices.get(j);
            Float actualPrice = actualPrices.get(j);

            float discount = (float) (100.0 - (actualPrice * 100.0) / oldPrice);
            float expectedPrice = oldPrice - (oldPrice / 100 * discount);

            softAssert.assertEquals(actualPrice, expectedPrice, productNames.get(j) + ": ");
        }
        softAssert.assertAll();
    }
}