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
      model.put("users", User.all());
      model.put("template", "templates/index.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      String name = request.queryParams("name");
      int budget = Integer.parseInt(request.queryParams("budget"));
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

  }
}
