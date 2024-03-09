package botanical.harmony.kiwi;

import java.util.Optional;

public class QueryResponse<T> {
  private final boolean successful;
  private final String message;
  private final Optional<T> result;

  public QueryResponse(boolean successful, String message) {
    this.successful = successful;
    this.message = message;
    result = Optional.empty();
  }

  public QueryResponse(boolean successful, String message, T result) {
    this.successful = successful;
    this.message = message;
    this.result = Optional.of(result);
  }

  public static <T> QueryResponse<T> failed(String message) {
    return new QueryResponse<>(false, message);
  }

  public static <T> QueryResponse<T> success(T result, String message) {
    return new QueryResponse<>(true, message, result);
  }

  public boolean isSuccessful() {
    return successful;
  }

  public String getMessage() {
    return message;
  }

  public Optional<T> getResult() {
    return result;
  }
}
