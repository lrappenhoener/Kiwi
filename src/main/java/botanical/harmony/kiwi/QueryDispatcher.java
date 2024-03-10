package botanical.harmony.kiwi;

import java.util.Optional;

class QueryDispatcher {
  private final Handlers<QueryHandler> handlers;

  QueryDispatcher(Handlers<QueryHandler> handlers) {
    this.handlers = handlers;
  }

  <T> QueryResponse<T> send(Query<T> query) {
    Optional<QueryHandler> optionalQueryHandler = handlers.getBy(query);
    if (optionalQueryHandler.isEmpty())
      return QueryResponse.failed("not registered");
    QueryHandler handler = optionalQueryHandler.get();
    return handler.handle(query);
  }
}
