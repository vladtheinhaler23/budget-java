import java.util.List;
import org.sql2o.*;

public class Transaction {
  private int id;
  private int amount;
  private int user_id;

  public Transaction(int amount) {
    this.amount = amount;
  }

  public Transaction(Integer amount, int user_id) {
    this.amount = amount;
    this.user_id = user_id;
  }

  public Integer getAmount() {
  return amount;
  }

  public int getId() {
    return id;
  }

  public int getUserId() {
    return user_id;
  }

  public static List<Transaction> all() {
    String sql = "SELECT id, amount, user_id FROM transactions;";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Transaction.class);
    }
  }

  public void save() {
    try (Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO transactions (amount, user_id) VALUES (:amount, :user_id);";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("amount", this.amount)
        .addParameter("user_id", this.user_id)
        .executeUpdate()
        .getKey();
    }
  }

  public static Transaction find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM transactions WHERE id=:id;";
      Transaction transaction = con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Transaction.class);
      return transaction;
    }
  }

  @Override
  public boolean equals(Object otherTransaction) {
    if (!(otherTransaction instanceof Transaction)) {
      return false;
    } else {
      Transaction newTransaction = (Transaction) otherTransaction;
      return this.getAmount().equals(newTransaction.getAmount()) &&
             this.getId() == newTransaction.getId() &&
             this.getUserId() == newTransaction.getUserId();
    }
  }


}
