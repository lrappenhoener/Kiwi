package botanical.harmony.kiwi;

public interface HandlerProvider {
  boolean canProvide(Class<?> clazz);
  <T> T provide(Class<T> clazz);
}
