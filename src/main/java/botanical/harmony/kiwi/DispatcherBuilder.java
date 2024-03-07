package botanical.harmony.kiwi;

import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class DispatcherBuilder {
  private final Map<Class<? extends Command>, CommandHandler<? extends Command>> commandHandlers = new HashMap<>();
  private final Map<Class<? extends Command>, Class<? extends CommandHandler<?>>> commandHandlerClasses = new HashMap<>();
  private final Optional<HandlerProvider> optionalHandlerProvider;

  private DispatcherBuilder(Optional<HandlerProvider> optionalHandlerProvider) {
    this.optionalHandlerProvider = optionalHandlerProvider;
  }

  public static DispatcherBuilder create() {
    return new DispatcherBuilder(Optional.empty());
  }

  public static DispatcherBuilder create(HandlerProvider provider) {
    return new DispatcherBuilder(Optional.of(provider));
  }

  public Dispatcher build() {
    return Dispatcher.create(commandHandlers, commandHandlerClasses, optionalHandlerProvider);
  }

  public <TCommand extends Command> DispatcherBuilder register(
          CommandHandler<TCommand> handler) {
    Class<TCommand> commandClass = getCommandClass(handler);
    commandHandlers.put(commandClass, handler);
    return this;
  }

  public <TCommand extends Command> DispatcherBuilder register(Class<? extends CommandHandler<TCommand>> handlerClass) {
    if (optionalHandlerProvider.isEmpty()) throw new MissingProviderException(
            "You need to create the builder with an provider to create handler instances if you want to register by class");
    Class<TCommand> commandClass = getCommandClass(handlerClass);
    commandHandlerClasses.put(commandClass, handlerClass);
    return this;
  }

  public boolean hasCommandHandlerFor(Class<? extends Command> commandClass) {
    return commandHandlers.containsKey(commandClass) || commandHandlerClasses.containsKey(
            commandClass);
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
}
