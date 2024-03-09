package botanical.harmony.kiwi;

import java.util.Map;
import java.util.Optional;

public class Dispatcher {
  private final Map<Class<? extends Command>, CommandHandler<? extends Command>> commandHandlers;
  private final Map<Class<? extends Command>, Class<? extends CommandHandler<?>>> commandHandlerClasses;
  private final Map<Class<? extends Query>, QueryHandler> queryHandlers;
  private final Map<Class<? extends Query>, Class<? extends QueryHandler<?, ?>>> queryHandlerClasses;
  private final Optional<HandlerProvider> optionalHandlerProvider;

  private Dispatcher(
          Map<Class<? extends Command>, CommandHandler<? extends Command>> commandHandlers,
          Map<Class<? extends Command>, Class<? extends CommandHandler<?>>> commandHandlerClasses,
          Map<Class<? extends Query>, QueryHandler> queryHandlers,
          Map<Class<? extends Query>, Class<? extends QueryHandler<?, ?>>> queryHandlerClasses,
          Optional<HandlerProvider> optionalHandlerProvider) {
    this.commandHandlers = commandHandlers;
    this.commandHandlerClasses = commandHandlerClasses;
    this.queryHandlers = queryHandlers;
    this.queryHandlerClasses = queryHandlerClasses;
    this.optionalHandlerProvider = optionalHandlerProvider;
  }

  public static Dispatcher create(
          Map<Class<? extends Command>, CommandHandler<? extends Command>> commandHandlers,
          Map<Class<? extends Command>, Class<? extends CommandHandler<?>>> commandHandlerClasses,
          Map<Class<? extends Query>, QueryHandler> queryHandlers,
          Map<Class<? extends Query>, Class<? extends QueryHandler<?, ?>>> queryHandlerClasses,
          Optional<HandlerProvider> optionalHandlerProvider) {
    return new Dispatcher(
            commandHandlers,
            commandHandlerClasses,
            queryHandlers,
            queryHandlerClasses,
            optionalHandlerProvider
    );
  }

  public CommandResponse send(
          Command command) {
    Class<? extends Command> commandClass = command.getClass();
    if (commandHandlers.containsKey(commandClass)) {
      CommandHandler commandHandler = commandHandlers.get(commandClass);
      return commandHandler.handle(command);
    }
    if (commandHandlerClasses.containsKey(commandClass)) {
      Class<? extends CommandHandler<?>> commandHandlerClass = commandHandlerClasses.get(
              commandClass);
      CommandHandler commandHandler = optionalHandlerProvider.get().provide(commandHandlerClass);
      return commandHandler.handle(command);
    }
    return CommandResponse.failed("no handler registered");
  }

  public <T> QueryResponse<T> send(Query<T> query) {
    Class<? extends Query> queryClass = query.getClass();
    if (queryHandlers.containsKey(queryClass)) {
      QueryHandler queryHandler = queryHandlers.get(queryClass);
      return queryHandler.handle(query);
    }
    return QueryResponse.failed("no handler registered");
  }
}
