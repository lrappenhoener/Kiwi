package botanical.harmony.kiwi;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class TestProvider implements HandlerProvider {
  private final Map<Class<?>, Supplier<?>> handlerFactories = new HashMap<>();

  public static TestProvider create() {
    TestProvider testProvider = new TestProvider();
    testProvider.registerCommandHandler(TestCommandHandler.class, () -> new TestCommandHandler(c -> {}) );
    testProvider.registerQueryHandler(TestQueryHandler.class, () -> new TestQueryHandler());
    return testProvider;
  }

  @Override
  public boolean canProvide(Class<?> clazz) {
    return true;
  }

  @Override
  public <T> T provide(Class<T> clazz) {
    return (T)handlerFactories.get(clazz).get();
  }

  public TestProvider registerCommandHandler(
          Class<?> clazz,
          Supplier<CommandHandler<?>> commandHandlerFactory) {
    handlerFactories.put(clazz, commandHandlerFactory);
    return this;
  }
  public TestProvider registerQueryHandler(
          Class<?> clazz,
          Supplier<QueryHandler> queryHandlerFactory) {
    handlerFactories.put(clazz, queryHandlerFactory);
    return this;
  }
}
