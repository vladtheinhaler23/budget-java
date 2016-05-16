import org.junit.rules.ExternalResource;
import org.sql2o.*;

public class DatabaseRule extends ExternalResource {

  @Override
  protected void before() {
    DB.sql2o = new Sql2o("jdbc:postgresql://localhost:5432/budget_test", null, null);
  }

  @Override
  protected void after() {
    try(Connection con = DB.sql2o.open()) {
      String deleteTransactionsQuery = "DELETE FROM transactions *;";
      String deleteUsersQuery = "DELETE FROM users *;";
      con.createQuery(deleteTransactionsQuery).executeUpdate();
      con.createQuery(deleteUsersQuery).executeUpdate();
    }
  }

}
