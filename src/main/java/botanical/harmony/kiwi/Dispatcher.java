package botanical.harmony.kiwi;

public class Dispatcher {
  private Dispatcher() {
  }

  public static Dispatcher create() {
    return new Dispatcher();
  }

  public CommandResponse dispatch(Command command) {
    return CommandResponse.failed("no handler registered");
  }
}
