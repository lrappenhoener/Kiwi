package botanical.harmony.kiwi;

import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.Map;

public class DispatcherBuilder {
  private final Map<Class<? extends Command>,
          CommandHandler<? extends Command>> commandHandlers = new HashMap<>();

  public static DispatcherBuilder create() {
    return new DispatcherBuilder();
  }

  public <TCommand extends Command> DispatcherBuilder register(
          CommandHandler<TCommand> handler) {
    Class<TCommand> commandClass = getCommandClassFromHandler(handler);
    commandHandlers.put(commandClass, handler);
    return this;
  }

  private <TCommand extends Command> Class<TCommand> getCommandClassFromHandler(
          CommandHandler<TCommand> handler) {
    ParameterizedType parameterizedType =
            (ParameterizedType) handler.getClass().getGenericInterfaces()[0];
    return (Class<TCommand>) parameterizedType.getActualTypeArguments()[0];
  }

  public Dispatcher build() {
    return new Dispatcher();
  }

  public boolean hasCommandHandlerFor(Class<? extends Command> commandClass) {
    return commandHandlers.containsKey(commandClass);
  }
}
