package botanical.harmony.kiwi;

public interface HandlerProvider {
  boolean canProvide(Class<?> clazz);

  CommandHandler provide(Class<? extends CommandHandler<?>> commandHandlerClass);
}
