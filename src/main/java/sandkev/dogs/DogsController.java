package sandkev.dogs;

import jdk.nashorn.internal.objects.annotations.Constructor;
import lombok.AllArgsConstructor;
import lombok.Value;

import static spark.Spark.get;
import static spark.Spark.options;
import static spark.Spark.post;
import static spark.Spark.delete;

import java.util.List;

@Value
@AllArgsConstructor(staticName = "of")
public class DogsController {
    MockDogProvider service;

    public void register() {

        get("/dogs", (request, response) -> service.getDogs());

        // matches "GET /hello/foo" and "GET /hello/bar"
        // request.params(":name") is 'foo' or 'bar'
        get("/dogs/:id", (request, response) -> {
            String id = request.params(":id");
            return service.findDogById(Integer.parseInt(id));
        });


    }


}

/*
import static spark.Spark.*;

public class UserController {

  public UserController(final UserService userService) {

    get("/users", new Route() {
      @Override
      public Object handle(Request request, Response response) {
        // process request
        return userService.getAllUsers();
      }
    });

    // more routes
  }
}
 */