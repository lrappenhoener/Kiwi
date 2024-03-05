package botanical.harmony.kiwi;
import java.util.function.Consumer;

public class TestCommandHandler implements CommandHandler<TestCommand> {
  private final Consumer<TestCommand> testCommandConsumer;

  public TestCommandHandler(Consumer<TestCommand> testCommandConsumer) {
    this.testCommandConsumer = testCommandConsumer;
  }

  @Override
  public CommandResponse handle(TestCommand testCommand) {
    testCommandConsumer.accept(testCommand);
    return CommandResponse.success();
  }
}
