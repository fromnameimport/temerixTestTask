package base;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;

import java.util.Objects;

import static com.codeborne.selenide.Selenide.$;

public class TestBase {

    public void openHomePage() {
        Selenide.open(Configuration.baseUrl);
    }

    public boolean isUkrainianLanguage() {
        return Objects.requireNonNull(
                $("div.language-list > a[data-lang=\"uk\"]").getAttribute("class")
        ).contains("active");
    }

    public void returnToTheHomePage() {
        $("a.middle-logo").click();
    }
}
