package botanical.harmony.kiwi;

import botanical.harmony.strawberry.Container;
import botanical.harmony.strawberry.ContainerBuilder;
import org.junit.jupiter.api.Test;

public class SomeTest {
  @Test
  void foo() {
    ContainerBuilder containerBuilder = ContainerBuilder.create();
    containerBuilder.register(TestCommandHandler.class);
    Container container = containerBuilder.build();

    CommandDispatcherBuilder dispatcherBuilder = CommandDispatcherBuilder.create(container);
    dispatcherBuilder.register(TestCommandHandler.class, TestCommand.class);
    CommandDispatcher commandDispatcher = dispatcherBuilder.build();
    TestCommand command = new TestCommand("Test", 42);

    commandDispatcher.dispatch(command);
  }
}

interface CommandHandlerProvider {
  <T> T get(Class<T> handlerClass);
}

class CommandDispatcher {

  public void dispatch(Object command) {
    command.getClass();
  }
}

class CommandDispatcherBuilder {

  public static CommandDispatcherBuilder create(Container container) {
    return new CommandDispatcherBuilder();
  }

  public CommandDispatcher build() {
    return null;
  }

  public CommandDispatcherBuilder register(Class<? extends CommandHandler> handler, Class<?> command) {

  }
}
class TestCommandHandler implements CommandHandler<TestCommand, String> {

  @Override
  public String handle(TestCommand command) {
    return command.message;
  }
}

class TestCommand implements Command {
  public final String message;

  public TestCommand(String message) {
    this.message = message;
  }
}

interface CommandHandler<TCommand extends Command, TResult> {
  TResult handle(TCommand command);
}

interface Command {}

