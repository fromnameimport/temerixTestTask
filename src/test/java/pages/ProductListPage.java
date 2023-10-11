package pages;

import base.TestBase;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class ProductListPage extends TestBase {

    // variables
//    private SelenideElement loadMore30ItemsButton = $("div.pagination-block-load-more");
    private SelenideElement loadMore30ItemsButton = $(By.className("pagination-block-load-more"));
    private final ElementsCollection regularPrices = $$("div.center-part> div > div.regular-price");
//    private final ElementsCollection oldPrices = $$("div.old-price");
//    private final ElementsCollection oldPrices = $$(By.className("old-price"));
    // nth child?

    private ElementsCollection paginationItems = $$("div.pagination > a");

    // getters
    public int getNthItemRegularPrice(int itemNum) {
        return Integer.parseInt(regularPrices.get(itemNum).getText().replaceAll(" ", ""));
    }

    public int getNthItemOldPrice(int itemNum) {
        String selector = "div.products-listing.list > div:nth-child("+itemNum+") > div.content-block > div.center-part > div.price-block > div.old-price";
        return Integer.parseInt($(selector).getText().replaceAll(" ", ""));
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

}
