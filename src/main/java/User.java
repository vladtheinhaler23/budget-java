import java.util.List;
import org.sql2o.*;

public class User {
  private int id;
  private String name;
  private int budget;

  public User(String name, int budget) {
    this.name = name;
    this.budget = budget;
  }

  public String getName() {
    return name;
  }

  public int getBudget() {
    return budget;
  }

  public int getId() {
    return id;
  }

  public static List<User> all() {
    String sql = "SELECT id, name FROM users;";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(User.class);
    }
  }

  public void save() {
    try (Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO users (name, budget) VALUES (:name, :budget);";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("name", this.name)
        .addParameter("budget", this.budget)
        .executeUpdate()
        .getKey();
    }
  }

  // public static String getTotalSpent() {
  //   try (Connection con = DB.sql2o.open()) {
  //     String sql = "SELECT sum(amount) FROM transactions";
  //     Query query = con.createQuery(sql);
  //
  //     ResultSet rs = query.executeQuery();
  //
  //
  //     return rs;
  //   }

  }

  public static User find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM users WHERE id=:id;";
      User user = con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(User.class);
      return user;
    }
  }

  public List<Transaction> getTransactions() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM transactions where user_id=:id;";
      return con.createQuery(sql)
        .addParameter("id", this.id)
        .executeAndFetch(Transaction.class);
    }
  }

  @Override
  public boolean equals(Object otherUser) {
    if (!(otherUser instanceof User)) {
      return false;
    } else {
      User newUser = (User) otherUser;
      return this.getName().equals(newUser.getName()) &&
             this.getId() == newUser.getId();
    }
  }

  public void update(String name) {
  try(Connection con = DB.sql2o.open()) {
    String sql = "UPDATE users SET name = :name WHERE id = :id";
    con.createQuery(sql)
      .addParameter("name", name)
      .addParameter("id", id)
      .executeUpdate();
    }
  }

  public void delete() {
    try(Connection con = DB.sql2o.open()) {
    String sql = "DELETE FROM users WHERE id = :id;";
    con.createQuery(sql)
      .addParameter("id", id)
      .executeUpdate();
    }
}


}
