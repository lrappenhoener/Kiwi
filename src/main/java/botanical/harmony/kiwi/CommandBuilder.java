package botanical.harmony.kiwi;

import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class CommandBuilder {
  private final Map<Class<? extends Command>, CommandHandler> commandHandlers = new HashMap<>();
  private final Map<Class<? extends Command>, Class<? extends CommandHandler>> commandHandlerClasses = new HashMap<>();
  private final Optional<HandlerProvider> optionalHandlerProvider;

  public CommandBuilder(Optional<HandlerProvider> optionalHandlerProvider) {
    this.optionalHandlerProvider = optionalHandlerProvider;
  }

  public static CommandBuilder create(Optional<HandlerProvider> optionalHandlerProvider) {
    return new CommandBuilder(optionalHandlerProvider);
  }

  public CommandDispatcher build() {
    Handlers handlers = new Handlers(
            commandHandlers,
            commandHandlerClasses,
            optionalHandlerProvider
    );
    return new CommandDispatcher(handlers);
  }

  public <TCommand extends Command> void register(CommandHandler<TCommand> handler) {
    Class<TCommand> commandClass = getCommandClass(handler);
    commandHandlers.put(commandClass, handler);
  }

  public <TCommand extends Command> void register(Class<? extends CommandHandler<TCommand>> commandHandlerClass) {
    if (optionalHandlerProvider.isEmpty()) throw new MissingProviderException(
            "You need to create the builder with an provider to create handler instances if you want to register by class");
    Class<TCommand> commandClass = getCommandClass(commandHandlerClass);
    commandHandlerClasses.put(commandClass, commandHandlerClass);
  }
  private <TCommand extends Command> Class<TCommand> getCommandClass(
          CommandHandler<TCommand> handler) {
    Class<? extends CommandHandler> handlerClass = handler.getClass();
    return getCommandClass(handlerClass);
  }

  private <TCommand extends Command> Class<TCommand> getCommandClass(Class<? extends CommandHandler> handlerClass) {
    ParameterizedType parameterizedType = (ParameterizedType) handlerClass.getGenericInterfaces()[0];
    return (Class<TCommand>) parameterizedType.getActualTypeArguments()[0];
  }

  public boolean hasCommandHandlerFor(Class<? extends Command> commandClass) {
    return commandHandlers.containsKey(commandClass) || commandHandlerClasses.containsKey(commandClass);
  }
}
