package botanical.harmony.kiwi.helper;
import botanical.harmony.kiwi.CommandHandler;
import botanical.harmony.kiwi.CommandResponse;

import java.util.function.Consumer;

public class TestCommandHandler implements CommandHandler<TestCommand> {
  private final Consumer<TestCommand> testCommandConsumer;

  public TestCommandHandler(Consumer<TestCommand> testCommandConsumer) {
    this.testCommandConsumer = testCommandConsumer;
  }

  @Override
  public CommandResponse handle(TestCommand testCommand) {
    testCommandConsumer.accept(testCommand);
    return CommandResponse.success(testCommand);
  }
}
