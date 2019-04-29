package sandkev.dogs;

import jdk.nashorn.internal.objects.annotations.Constructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import static spark.Spark.get;
import static spark.Spark.options;
import static spark.Spark.post;
import static spark.Spark.delete;

import java.util.List;
import java.util.Optional;

@Value
@AllArgsConstructor(staticName = "of")
@Builder(buildMethodName = "buildInternal")
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

    /** Add some functionality to the generated builder class */
    public static class DogsControllerBuilder {
        public Optional<DogsController> buildOptional() {
            return Optional.of(this.build());
        }

        public DogsController build() {
            DogsController controller = this.buildInternal();
            controller.register();
            return controller;
        }
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