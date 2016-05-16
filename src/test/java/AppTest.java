import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.sql2o.*;
import org.junit.*;
import org.fluentlenium.adapter.FluentTest;
import org.junit.ClassRule;

import static org.assertj.core.api.Assertions.assertThat;

public class AppTest extends FluentTest {
  public WebDriver webDriver = new HtmlUnitDriver();

  @Override
  public WebDriver getDefaultDriver() {
    return webDriver;
  }


  @ClassRule
  public static ServerRule server = new ServerRule();

  @Rule
  public DatabaseRule database = new DatabaseRule();

    @Test
    public void rootTest() {
      goTo("http://localhost:4567/");
      assertThat(pageSource()).contains("BUDget");
    }

    @Test
    public void userIsCreatedTest() {
      goTo("http://localhost:4567/");
      fill("#name").with("Fozzie Bear");
      submit(".btn");
      assertThat(pageSource()).contains("Fozzie Bear");
    }

    @Test
    public void userPageIsDisplayedTest() {
      User myUser = new User("Fozzie Bear", 200);
      myUser.save();
      String userPath = String.format("http://localhost:4567/user/%d", myUser.getId());
      goTo(userPath);
      assertThat(pageSource()).contains("Fozzie Bear");
    }

    @Test
    public void transactionsAreAddedAndDisplayed() {
      goTo("http://localhost:4567/");
      fill("#name").with("Fozzie Bear");
      submit(".btn");
      fill("#amount").with("500");
      submit(".btn");
      assertThat(pageSource()).contains("500");
    }


}
