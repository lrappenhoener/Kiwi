package botanical.harmony.kiwi;

import java.lang.reflect.ParameterizedType;
import java.util.Optional;

public class DispatcherBuilder {
  private final QueryBuilder queryBuilder;
  private final CommandBuilder commandBuilder;

  private DispatcherBuilder(Optional<HandlerProvider> optionalHandlerProvider) {
    queryBuilder = QueryBuilder.create(optionalHandlerProvider);
    commandBuilder = CommandBuilder.create(optionalHandlerProvider);
  }

  public static DispatcherBuilder create() {
    return new DispatcherBuilder(Optional.empty());
  }

  public static DispatcherBuilder create(HandlerProvider provider) {
    return new DispatcherBuilder(Optional.of(provider));
  }

  public Dispatcher build() {
    CommandDispatcher commandDispatcher = commandBuilder.build();
    QueryDispatcher queryDispatcher = queryBuilder.build();
    return Dispatcher.create(commandDispatcher, queryDispatcher);
  }

  public <TCommand extends Command> DispatcherBuilder registerCommandHandler(
          CommandHandler<TCommand> handler) {
    commandBuilder.register(handler);
    return this;
  }

  public <TCommand extends Command> DispatcherBuilder registerCommandHandler(Class<? extends CommandHandler<TCommand>> handlerClass) {
    commandBuilder.register(handlerClass);
    return this;
  }

  public <T extends QueryHandler> DispatcherBuilder registerQueryHandler(T queryHandler) {
    queryBuilder.register(queryHandler);
    return this;
  }

  public <T extends QueryHandler> DispatcherBuilder registerQueryHandler(Class<T> queryHandlerClass) {
    queryBuilder.register(queryHandlerClass);
    return this;
  }

  public boolean hasCommandHandlerFor(Class<? extends Command> commandClass) {
   return commandBuilder.hasCommandHandlerFor(commandClass);
  }


}
