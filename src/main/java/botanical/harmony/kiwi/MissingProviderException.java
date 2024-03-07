package botanical.harmony.kiwi;

public class MissingProviderException extends RuntimeException {
  private final String message;

  public MissingProviderException(String message){

    this.message = message;
  }

  @Override
  public String getMessage() {
    return message;
  }
}
