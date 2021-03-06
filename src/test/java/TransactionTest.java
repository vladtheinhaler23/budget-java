import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;

public class TransactionTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void Transaction_instantiatesCorrectly_true() {
    Transaction myTransaction = new Transaction(500, 1);
    assertEquals(true, myTransaction instanceof Transaction);
  }

  @Test
  public void getAmount_transactionInstantiatesWithAmount_String() {
    Transaction myTransaction = new Transaction(300, 1);
    assertEquals(300, myTransaction.getAmount());
  }

  @Test
  public void all_emptyAtFirst() {
    assertEquals(Transaction.all().size(), 0);
  }

  @Test
  public void equals_returnsTrueIfAmountsAreTheSame() {
    Transaction firstTransaction = new Transaction(100, 2);
    Transaction secondTransaction = new Transaction(100, 2);
    assertTrue(firstTransaction.equals(secondTransaction));
  }

  @Test
  public void save_returnsTrueIfAmountsAretheSame() {
    Transaction myTransaction = new Transaction(200, 3);
    myTransaction.save();
    assertTrue(Transaction.all().get(0).equals(myTransaction));
  }

  @Test
  public void save_assignsIdToObject() {
    Transaction myTransaction = new Transaction(300, 4);
    myTransaction.save();
    Transaction savedTransaction = Transaction.all().get(0);
    assertEquals(myTransaction.getId(), savedTransaction.getId());
  }

  @Test
  public void find_findsTransactionInDatabase_true() {
    Transaction myTransaction = new Transaction(400, 5);
    myTransaction.save();
    Transaction savedTransaction = Transaction.find(myTransaction.getId());
    assertTrue(myTransaction.equals(savedTransaction));
  }

  @Test
  public void save_savesUserIdIntoDB_true() {
    User myUser = new User("Sam the Eagle", 200);
    myUser.save();
    Transaction myTransaction = new Transaction(100, myUser.getId());
    myTransaction.save();
    Transaction savedTransaction = Transaction.find(myTransaction.getId());
    assertEquals(savedTransaction.getUserId(), myUser.getId());
  }

  @Test
  public void update_updatesTransactionDescription_true() {
    Transaction myTransaction = new Transaction(400, 1);
    myTransaction.save();
    myTransaction.update(450);
    assertEquals(450, Transaction.find(myTransaction.getId()).getAmount());
  }

  @Test
  public void delete_deletesTransaction_true() {
    Transaction myTransaction = new Transaction(250, 1);
    myTransaction.save();
    int myTransactionId = myTransaction.getId();
    myTransaction.delete();
    assertEquals(null, Transaction.find(myTransactionId));
  }


}
