package botanical.harmony.kiwi.helper;

import botanical.harmony.kiwi.CommandHandler;
import botanical.harmony.kiwi.CommandResponse;

public class ThrowingTestCommandHandler implements CommandHandler<TestCommand> {
  @Override
  public CommandResponse handle(TestCommand command) {
    throw new RuntimeException();
  }
}
