import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.sql2o.*;
import org.junit.*;
import org.fluentlenium.adapter.FluentTest;
import org.junit.ClassRule;
import static org.junit.Assert.*;
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

    @Test
    public void transactionUpdate() {
      User myUser = new User("Beaker", 100);
      myUser.save();
      Transaction myTransaction = new Transaction(20, myUser.getId());
      myTransaction.save();
      String transactionPath = String.format("http://localhost:4567/users/%d/transactions/%d", myUser.getId(), myTransaction.getId());
      goTo(transactionPath);
      fill("#amount").with("30");
      submit("update-transaction");
      assertThat(pageSource()).contains("30");
    }

    @Test
    public void transactionDelete() {
      User myUser = new User("Animal", 100);
      myUser.save();
      Transaction myTransaction = new Transaction(20, myUser.getId());
      myTransaction.save();
      String transactionPath = String.format("http://localhost:4567/categories/%d/transactions/%d", myUser.getId(), myTransaction.getId());
      goTo(transactionPath);
      submit("#delete-transaction");
      assertEquals(0, Transaction.all().size());
    }

    @Test
    public void userNameIsUpdated() {
      User testUser = new User("Kermie");
      testUser.save();
      String url = String.format("http://localhost:4567/users/%d", testUser.getId());
      goTo(url);
      fill("#update").with("Kermit");
      submit("#update-submit");
      assertThat(pageSource().contains("Kermit"));
    }

    @Test
    public void userIsDeleted() {
      User testUser = new User("Piggy");
      testUser.save();
      String url = String.format("http://localhost:4567/users/%d", testUser.getId());
      goTo(url);
      submit("#delete-user");
      assertFalse(pageSource().contains("Piggy"));
    }

}
