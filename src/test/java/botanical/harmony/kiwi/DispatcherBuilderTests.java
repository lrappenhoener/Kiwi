package botanical.harmony.kiwi;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DispatcherBuilderTests {
  @Test
  void correctly_knows_if_handler_is_registered(){
    DispatcherBuilder builder = DispatcherBuilder.create();
    TestCommandHandler handler = new TestCommandHandler((c) -> {});
    builder.register(handler);

    assertTrue(builder.hasCommandHandlerFor(TestCommand.class));
  }

  @Test
  void correctly_knows_if_handler_is_not_registered() {
    DispatcherBuilder builder = DispatcherBuilder.create();

    assertFalse(builder.hasCommandHandlerFor(TestCommand.class));
  }
}
