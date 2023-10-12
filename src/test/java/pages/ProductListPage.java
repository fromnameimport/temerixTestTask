package pages;

import base.TestBase;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class ProductListPage extends TestBase {

    // variables
    private final SelenideElement loadMore30ItemsButton = $(By.className("pagination-block-load-more"));
    private final ElementsCollection itemNames = $$(By.xpath("//a[@class=\"name-block\"]"));
    private final ElementsCollection regularPrices = $$("div.center-part> div > div.regular-price");

    private final ElementsCollection paginationItems = $$("div.pagination > a");


    // getters
    public float getNthItemRegularPrice(int itemNum) {
        return Float.parseFloat(regularPrices.get(itemNum - 1).getText().replaceAll("[^0-9.]", ""));
    }

    public float getNthItemOldPrice(int itemNum) {
        SelenideElement itemOldPrice = $("div.products-listing.list > div:nth-child("+itemNum+") > div.content-block > div.center-part > div.price-block > div.old-price");
        if (!itemOldPrice.exists()) {
            return 0;
        }
        return Float.parseFloat(itemOldPrice.getText().replaceAll("[^0-9.]", ""));
    }

    public int getItemsQty() {
        return itemNames.size();
    }

    public SelenideElement getNthPaginationItem(int paginationItemNum) {
        return paginationItems.get(paginationItemNum);
    }

    public SelenideElement getLoadMore30ItemsButton() {
        return loadMore30ItemsButton;
    }

    // navigation
    public ProductListPage clickNthPaginationItem(int paginationItemNum) {
        getNthPaginationItem(paginationItemNum).click();
        return this;
    }

    public ProductListPage clickLoad30MoreItems() {
        getLoadMore30ItemsButton().click();
        return this;
    }

    public void clickNthItemName(int itemNUm) {
        SelenideElement item = $("div.products-listing.list > div:nth-child("+itemNUm+") > div.image-block > a");
        item.click();
    }
}
