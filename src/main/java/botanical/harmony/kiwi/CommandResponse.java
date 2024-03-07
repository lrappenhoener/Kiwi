package botanical.harmony.kiwi;

public class CommandResponse {
  private final String message;
  private final boolean successful;

  private CommandResponse(boolean successful, String message) {
    this.successful = successful;
    this.message = message;
  }

  public static CommandResponse success(String message) {
    return new CommandResponse(true, message);
  }

  public static CommandResponse success(Command command) {
   return new CommandResponse(true, String.format("Successful handled %s", command.getClass()));
  }

  public static CommandResponse failed(String message) {
    return new CommandResponse(false, message);
  }

  public boolean isSuccessful() {
    return successful;
  }

  public String getMessage() {
    return message;
  }
}
