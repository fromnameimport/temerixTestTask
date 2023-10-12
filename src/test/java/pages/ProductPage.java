package pages;

import base.TestBase;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class ItemPage extends TestBase {
    private final SelenideElement itemCurrentPrice = $("div.product-info-block > div > div.center-part > div.price-block > div.regular-price");
    private final SelenideElement itemOldPrice = $(" div.product-info-block > div > div.center-part > div.price-block > div.old-price");

    // getters
    public float getItemRegularPrice() {
        return Float.parseFloat(itemCurrentPrice.getText().replaceAll("[^0-9.]", ""));
    }

    public float getItemOldPrice() {
        if (!itemOldPrice.exists()) {
            return 0;
        }
        return Float.parseFloat(itemOldPrice.getText().replaceAll("[^0-9.]", ""));
    }

    // assertions
    public boolean isCurrentItemPriceExists() {
        return itemCurrentPrice.exists();
    }

    public boolean isOldItemPriceExists() {
        return itemOldPrice.exists();
    }

    public boolean isPresent() {
        return 
    }
}
