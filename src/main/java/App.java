import java.util.Map;
import java.util.HashMap;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;
import static spark.Spark.*;

public class App {
  public static void main(String[] args) {
    staticFileLocation("/public");
    String layout = "templates/layout.vtl";

    get("/", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      // model.put("users", User.all());
      model.put("template", "templates/index.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/users", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("users", User.all());
      model.put("template", "templates/users.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/about-us", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("template", "templates/about-us.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/vision", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("template", "templates/vision.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      String name = request.queryParams("newUserName");
      int budget = Integer.parseInt(request.queryParams("newUserBudget"));
      User newUser = new User(name, budget);
      newUser.save();
      Transaction startingTransaction = new Transaction(0, newUser.getId());
      startingTransaction.save();
      model.put("users", User.all());
      model.put("template", "templates/index.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/user/:id", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("transactions", Transaction.all());
      User user = User.find(Integer.parseInt(request.params(":id")));
      model.put("user", user);
      model.put("template", "templates/user.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/user/:id", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      User user = User.find(Integer.parseInt(request.params(":id")));
      int amount = Integer.parseInt(request.queryParams("amount"));
      Transaction newTransaction = new Transaction(amount, user.getId());
      newTransaction.save();
      model.put("user", user);
      model.put("transactions", Transaction.all());
      model.put("template", "templates/user.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());



    post("/users/:user_id/transactions/:id", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Transaction transaction = Transaction.find(Integer.parseInt(request.params("id")));
      int amount = Integer.parseInt(request.queryParams("amount"));
      User user = User.find(transaction.getUserId());
      transaction.update(amount);
      String url = String.format("/users/%d/transactions/%d", user.getId(), transaction.getId());
      response.redirect(url);
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    // post("/users/:user_id/transactions/:id/delete", (request, response) -> {
    //   HashMap<String, Object> model = new HashMap<String, Object>();
    //   Transaction transaction = Transaction.find(Integer.parseInt(request.params("id")));
    //   User user = User.find(transaction.getUserId());
    //   transaction.delete();
    // }

    get("/transaction/new/:id", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      User user = User.find(Integer.parseInt(request.params(":id")));
      model.put("user", user);
      model.put("template", "templates/transaction-form.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/transaction/new/:id", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      User user = User.find(Integer.parseInt(request.params(":id")));
      model.put("user", user);
      model.put("template", "templates/transaction-form.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/transactions/delete_all/:id", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      User user = User.find(Integer.parseInt(request.params(":id")));
      user.deleteAll();
      Transaction newTransaction = new Transaction(0, user.getId());
      newTransaction.save();
      model.put("user", user);
      model.put("template", "templates/user.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

  }
}
