import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;

public class TransactionTest {

  // @Rule
  // public DatabaseRule database = new DatabaseRule();
  //
  // @Test
  // public void Transaction_instantiatesCorrectly_true() {
  //   Transaction myTransaction = new Transaction(500);
  //   assertEquals(true, myTransaction instanceof Transaction);
  // }
  //
  // @Test
  // public void getAmount_transactionInstantiatesWithAmount_String() {
  //   Transaction myTransaction = new Transaction(500);
  //   assertEquals(500, myTransaction.getAmount());
  // }
  //
  // @Test
  // public void all_emptyAtFirst() {
  //   assertEquals(Transaction.all().size(), 0);
  // }
  //
  // @Test
  // public void equals_returnsTrueIfAmountsAreTheSame() {
  //   Transaction firstTransaction = new Transaction(100);
  //   Transaction secondTransaction = new Transaction(100);
  //   assertTrue(firstTransaction.equals(secondTransaction));
  // }
  //
  // @Test
  // public void save_returnsTrueIfAmountsAretheSame() {
  //   Transaction myTransaction = new Transaction(200);
  //   myTransaction.save();
  //   assertTrue(Transaction.all().get(0).equals(myTransaction));
  // }
  //
  // @Test
  // public void save_assignsIdToObject() {
  //   Transaction myTransaction = new Transaction(300);
  //   myTransaction.save();
  //   Transaction savedTransaction = Transaction.all().get(0);
  //   assertEquals(myTransaction.getId(), savedTransaction.getId());
  // }
  //
  // @Test
  // public void find_findsTransactionInDatabase_true() {
  //   Transaction myTransaction = new Transaction(400);
  //   myTransaction.save();
  //   Transaction savedTransaction = Transaction.find(myTransaction.getId());
  //   assertTrue(myTransaction.equals(savedTransaction));
  // }
  //
  // @Test
  // public void save_savesUserIdIntoDB_true() {
  //   User myUser = new User("Sam the Eagle");
  //   myUser.save();
  //   Transaction myTransaction = new Transaction(100, myUser.getId());
  //   myTransaction.save();
  //   Transaction savedTransaction = Transaction.find(myTransaction.getId());
  //   assertEquals(savedTransaction.getUserId(), myUser.getId());
  // }



}
