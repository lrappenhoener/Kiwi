package botanical.harmony.kiwi;

import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class QueryBuilder {
  private final Optional<HandlerProvider> optionalHandlerProvider;
  private final Map<Class<? extends Query>, Class<? extends QueryHandler>> queryHandlerClasses = new HashMap<>();
  private final Map<Class<? extends Query>, QueryHandler> queryHandlers = new HashMap<>();

  public QueryBuilder(Optional<HandlerProvider> optionalHandlerProvider) {
    this.optionalHandlerProvider = optionalHandlerProvider;
  }

  public static QueryBuilder create(Optional<HandlerProvider> optionalHandlerProvider) {
    return new QueryBuilder(optionalHandlerProvider);
  }

  public QueryDispatcher build() {
    Handlers handlers = new Handlers(queryHandlers, queryHandlerClasses, optionalHandlerProvider);
    return new QueryDispatcher(handlers);
  }

  public <T extends QueryHandler> void register(T queryHandler) {
    Class<? extends QueryHandler> clazz = queryHandler.getClass();
    Class<? extends Query> queryClass = getQueryClass(clazz);
    queryHandlers.put(queryClass, queryHandler);
  }

  public <T extends QueryHandler> void register(Class<T> queryHandlerClass) {
    Class<? extends Query> queryClass = getQueryClass(queryHandlerClass);
    queryHandlerClasses.put(queryClass, queryHandlerClass);
  }

  private <TQuery extends Query> Class<TQuery> getQueryClass(Class<? extends QueryHandler> handlerClass) {
    ParameterizedType parameterizedType = (ParameterizedType) handlerClass.getGenericInterfaces()[0];
    return (Class<TQuery>) parameterizedType.getActualTypeArguments()[0];
  }
}
