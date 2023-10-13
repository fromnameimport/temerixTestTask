package pages;

import base.TestBase;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class ProductListPage extends TestBase {

    // variables
    private final SelenideElement loadMore30ItemsButton = $(By.className("pagination-block-load-more"));
    private final SelenideElement nextPaginationItem = $("a.pagination-item.next");
    private final SelenideElement actionSticker = $("span.sticker.stock");
    private final ElementsCollection productNames = $$(By.xpath("//a[@class=\"name-block\"]"));
    private final ElementsCollection regularPrices = $$("div.center-part> div > div.regular-price");
    private final ElementsCollection oldPrices = $$("div.center-part> div > div.old-price");
    private final ElementsCollection paginationItems = $$("div.pagination > a");


    // getters
    public float getNthItemRegularPrice(int itemNum) {
        return Float.parseFloat(regularPrices.get(itemNum - 1).getText().replaceAll("[^0-9.]", ""));
    }

    public float getNthItemRegularPriceByOldPriceElement(SelenideElement element) {
        return Float.parseFloat(element.ancestor("..price-block").find(By.className("regular-price")).getText().replaceAll("[^0-9.]", ""));
    }

    public float getNthItemOldPrice(int itemNum) {
        SelenideElement itemOldPrice = $("div.products-listing.list > div:nth-child("+itemNum+") > div.content-block > div.center-part > div.price-block > div.old-price");
        if (!itemOldPrice.exists()) {
            return 0;
        }
        return Float.parseFloat(itemOldPrice.getText().replaceAll("[^0-9.]", ""));
    }

    public SelenideElement getNthItemWithOldPrice(int itemNum) {
        return oldPrices.get(itemNum - 1);
    }

    public SelenideElement getNthItemOldPriceElement(int itemNum) {
        SelenideElement item = $("div.products-listing.list > div:nth-child(" + itemNum + ") > div.content-block > div.center-part > div.price-block > div.old-price");
        return item;
    }

    public int getItemsWithDiscountQty() {
        return oldPrices.size();
    }

    public int getItemsQty() {
        return productNames.size();
    }

    public SelenideElement getActionSticker() {
        return actionSticker;
    }

    public SelenideElement getNthPaginationItem(int paginationItemNum) {
        return paginationItems.get(paginationItemNum);
    }
    public SelenideElement getNextPaginationItem() { return nextPaginationItem; }

    public SelenideElement getLoadMore30ItemsButton() {
        return loadMore30ItemsButton;
    }

    public ElementsCollection getItemNames() {
        return productNames;
    }

    public String getNthItemName(int nth) {return getItemNames().get(nth - 1).getText(); }


    // navigation
    public ProductListPage clickNthPaginationItem(int paginationItemNum) {
        getNthPaginationItem(paginationItemNum).click();
        return this;
    }
    public ProductListPage clickNextPaginationItem() {
        getNextPaginationItem().click();
        return this;
    }

    public ProductListPage clickLoad30MoreItemsButton() {
        getLoadMore30ItemsButton().click();
        return this;
    }

    public ProductListPage clickLoad30MoreItemsButtonNthTimes(int nth) {
        for (int i = 1; i < nth; i++) {
            getLoadMore30ItemsButton().click();
            Selenide.sleep(1000);
        }
        return this;
    }

    public void clickNthItemName(int itemNUm) {
        SelenideElement item = $("div.products-listing.list > div:nth-child("+itemNUm+") > div.image-block > a");
        item.click();
    }


    // assertions
    public boolean isActionStickerPresentOnThePage() {
        return getActionSticker().isDisplayed();
    }

    public boolean isNthProductHasPbBankSticker(int nth) {
        SelenideElement pbSticker = $("div.products-listing.list > div:nth-child(" + nth + ") > div.content-block > div.center-part > div.bank-stickers > div.bank-sticker.pb");
        return pbSticker.exists();
    }
}
