import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;
import java.util.Arrays;

public class UserTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void User_instantiatesCorrectly_true() {
    User myUser = new User("Kermit", 100);
    assertEquals(true, myUser instanceof User);
  }

  @Test
  public void getName_UserInstantiatesWithName_String() {
    User myUser = new User("Gonzo", 200);
    assertEquals("Gonzo", myUser.getName());
  }

  @Test
  public void all_emptyAtFirst() {
    assertEquals(0, User.all().size());
  }

  @Test
  public void equals_returnsTrueIfNamesAreTheSame() {
    User firstUser = new User("Fozzy", 300);
    User secondUser = new User("Fozzy", 300);
    assertTrue(firstUser.equals(secondUser));
  }

  @Test
  public void save_returnsTrueIfNamesAretheSame() {
    User myUser = new User("Miss Piggy", 400);
    myUser.save();
    assertTrue(User.all().get(0).equals(myUser));
  }

  @Test
  public void save_savesIntoDatabase_true() {
    User myUser = new User("Animal", 100);
    myUser.save();
    User savedUser = User.all().get(0);
  }

  @Test
    public void save_assignsIdToObject() {
      User myUser = new User("Scooter", 200);
      myUser.save();
      User savedUser = User.all().get(0);
      assertEquals(myUser.getId(), savedUser.getId());
    }

  @Test
  public void find_findUserInDatabase_true() {
    User myUser = new User("Beaker", 300);
    myUser.save();
    User savedUser = User.find(myUser.getId());
    assertTrue(myUser.equals(savedUser));
  }

  @Test
  public void getTransactions_retrievesAllTransactionsFromDatabase_transactionsList() {
    User myUser = new User("Swedish Chef", 400);
    myUser.save();
    Transaction firstTransaction = new Transaction(100, myUser.getId());
    firstTransaction.save();
    Transaction secondTransaction = new Transaction(200, myUser.getId());
    secondTransaction.save();
    Transaction[] transactions = new Transaction[] { firstTransaction, secondTransaction };
    assertTrue(myUser.getTransactions().containsAll(Arrays.asList(transactions)));
  }

  @Test
  public void update_updatesUserName_true() {
    User myUser = new User("Fozzy", 100);
    myUser.save();
    myUser.update("Fozzie");
    assertEquals("Fozzie", User.find(myUser.getId()).getName());
  }

  @Test
  public void delete_deletesUser_true() {
    User myUser = new User("Gonzo", 100);
    myUser.save();
    int myUserId = myUser.getId();
    myUser.delete();
    assertEquals(null, User.find(myUserId));
  }

}
