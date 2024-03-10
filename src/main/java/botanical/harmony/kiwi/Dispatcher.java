package botanical.harmony.kiwi;

import java.util.Optional;

public class Dispatcher {
  private final CommandDispatcher commandDispatcher;
  private final QueryDispatcher queryDispatcher;

  private Dispatcher(
          CommandDispatcher commandDispatcher, QueryDispatcher queryDispatcher
          ) {
    this.commandDispatcher = commandDispatcher;
    this.queryDispatcher = queryDispatcher;
  }

  public static Dispatcher create(
          CommandDispatcher commandDispatcher, QueryDispatcher queryDispatcher) {
    return new Dispatcher(
            commandDispatcher,
            queryDispatcher
    );
  }

  public CommandResponse send(
          Command command) {
    return commandDispatcher.send(command);
  }

  public <T> QueryResponse<T> send(Query<T> query) {
    return queryDispatcher.send(query);
  }
}
