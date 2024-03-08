package botanical.harmony.kiwi;

public class QueryResponse<T> {
  private final boolean successful;
  private final String message;

  public QueryResponse(boolean successful, String message) {

    this.successful = successful;
    this.message = message;
  }

  public static <T> QueryResponse<T> failed(String message) {
    return new QueryResponse<>(false, message);
  }

  public boolean isSuccessful() {
    return successful;
  }

  public String getMessage() {
    return message;
  }
}
