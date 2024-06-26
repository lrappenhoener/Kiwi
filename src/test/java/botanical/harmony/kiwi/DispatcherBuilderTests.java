package botanical.harmony.kiwi;

import botanical.harmony.kiwi.helper.TestCommand;
import botanical.harmony.kiwi.helper.TestCommandHandler;
import botanical.harmony.kiwi.helper.TestProvider;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DispatcherBuilderTests {
  @Test
  void correctly_knows_if_handler_is_registered() {
    DispatcherBuilder builder = DispatcherBuilder.create();
    TestCommandHandler handler = new TestCommandHandler((c) -> {
    });
    builder.registerCommandHandler(handler);

    assertTrue(builder.hasCommandHandlerFor(TestCommand.class));
  }

  @Test
  void correctly_knows_if_handler_is_not_registered() {
    DispatcherBuilder builder = DispatcherBuilder.create();

    assertFalse(builder.hasCommandHandlerFor(TestCommand.class));
  }

  @Test
  void throws_missing_provider_exception_when_trying_register_handler_class_without_provider() {
    DispatcherBuilder builder = DispatcherBuilder.create();

    assertThrows(MissingProviderException.class, () -> builder.registerCommandHandler(
            TestCommandHandler.class));
  }

  @Test
  void correctly_knows_if_handler_class_registered() {
    TestProvider testProvider = TestProvider.create();
    DispatcherBuilder builder = DispatcherBuilder.create(testProvider);
    builder.registerCommandHandler(TestCommandHandler.class);

    assertTrue(builder.hasCommandHandlerFor(TestCommand.class));
  }

  @Test
  void correctly_knows_if_handler_class_not_registered() {
    TestProvider testProvider = TestProvider.create();
    DispatcherBuilder builder = DispatcherBuilder.create(testProvider);

    assertFalse(builder.hasCommandHandlerFor(TestCommand.class));
  }
}
