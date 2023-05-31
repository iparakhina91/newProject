import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;

public class Tests extends TestBase {

    public static String FAILEDBOOKNAME = "aaaaaa";
    public static String SEARCHRESULT = "harry potter";

    @Test
    void findBookByName() {
        open("https://litres.com/");
        $("input[name=q]").setValue("harry potter").pressEnter();
        $("#art_item_28295241").shouldHave(text("The Ultimate Harry Potter and Philosophy. Hogwarts for Muggles"));
    }

    @Test
    void findBookByAuthor() {
        open("https://litres.com/");
        $("input[name=q]").setValue("kristin hannah");
        $("#go[type=submit]").click();
        $$(".result-tabs").first().$("[data-tab=audio]").click();
        $("#art_item_42910814").shouldHave(text("Firefly Lane"));
    }

    @Test
    void findBookByPublisher() {
        open("https://litres.com/");
        $("input[name=q]").setValue("Eksmo Digital");
        $("#go[type=submit]").click();
        $(".result_book__list").shouldHave(text("Пророчество Гийома Завоевателя. Часть 1"));
    }

    @Test
    void findBooksByPreviousSearchResults() {
        open("https://litres.com/");
        $("input[name=q]").setValue(SEARCHRESULT).pressEnter();
        $("input[name=q]").clear();
        $("input[name=q]").click();
        $$("#nspotlight").first().$(".nspotlight__items").click();
        $("#b_search").shouldHave(text("Search results for «" + SEARCHRESULT + "»"));
    }

    @Test
    void findBookInNewReleases() {
        open("https://litres.com/");
        $("#newbooks").click();
        $$(".art__name").first().$("a").shouldHave(text("The Haunted Hotel: A Mystery of Modern Venice"));
    }

    @Test
    void findBookWithNonExistName() {
        open("https://litres.com/");
        $("input[name=q]").setValue(FAILEDBOOKNAME);
        $("#go[type=submit]").click();
        $(".ab-container.b_interested__book").shouldHave(text(
                "By request «" + FAILEDBOOKNAME + "» Nothing found"));
    }

    @Test
    void findBookWithEmptyName() {
        open("https://litres.com/");
        $("#go[type=submit]").click();
        $(".header_search_error").shouldHave(text(
                "Please enter three or more letters to search"));
    }
}
