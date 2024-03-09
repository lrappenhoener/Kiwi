package botanical.harmony.kiwi;

public interface QueryHandler<TQuery, TResult> {
  QueryResponse<TResult> handle(TQuery query);
}
