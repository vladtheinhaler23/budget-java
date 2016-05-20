import org.sql2o.*;
import org.junit.*;
import org.fluentlenium.adapter.FluentTest;
import org.junit.ClassRule;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import static org.assertj.core.api.Assertions.assertThat;
import static org.fluentlenium.core.filter.FilterConstructor.*;
import static org.junit.Assert.*;

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
      fill("#newUserName").with("Fozzie Bear");
      fill("#newUserBudget").with("200");
      submit("#newUserBtn");
      assertThat(pageSource()).contains("User List");
    }

    @Test
    public void userIsDeleted() {
      User testUser = new User("Piggy", 1);
      testUser.save();
      String url = String.format("http://localhost:4567/user/%d", testUser.getId());
      goTo(url);
      submit("#delete-user");
      assertFalse(pageSource().contains("Piggy"));
    }

}
