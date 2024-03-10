package botanical.harmony.kiwi;

import java.util.Map;
import java.util.Optional;

class Handlers<T> {
  private final Map<Class, T> handlers;
  private final Map<Class, Class> handlerClasses;
  private final Optional<HandlerProvider> optionalHandlerProvider;

  Handlers(
          Map<Class, T> handlers,
          Map<Class, Class> handlerClasses,
          Optional<HandlerProvider> optionalHandlerProvider) {
    this.handlers = handlers;
    this.handlerClasses = handlerClasses;
    this.optionalHandlerProvider = optionalHandlerProvider;
  }

   Optional<T> getBy(Object message) {
    Class clazz = message.getClass();

    if (handlers.containsKey(clazz)) {
      return Optional.of(handlers.get(clazz));
    }

    if (handlerClasses.containsKey(clazz)) {
      Class handlerClass = handlerClasses.get(clazz);
      HandlerProvider provider = optionalHandlerProvider.get();
      return Optional.of((T)provider.provide(handlerClass));
    }

    return Optional.empty();
  }
}
