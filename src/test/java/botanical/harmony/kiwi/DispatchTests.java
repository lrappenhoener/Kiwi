package botanical.harmony.kiwi;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DispatchTests {
  @Test
  void dispatching_command_without_registered_handler_instance_returns_failed_response() {
    DispatcherBuilder dispatcherBuilder = DispatcherBuilder.create();
    Dispatcher dispatcher = dispatcherBuilder.build();
    TestCommand command = new TestCommand(42);

    CommandResponse response = dispatcher.dispatch(command);

    assertFalse(response.isSuccessful());
    assertTrue(response.getMessage().contains("no handler registered"));
  }
  @Test
  void dispatching_command_with_handler_returns_successful_response() {
    DispatcherBuilder dispatcherBuilder = DispatcherBuilder.create();
    dispatcherBuilder.register(TestCommandHandler.class);
    Dispatcher dispatcher = dispatcherBuilder.build();
    TestCommand command = new TestCommand(42);

    CommandResponse response = dispatcher.dispatch(command);

    assertTrue(response.isSuccessful());
    assertTrue(response.getMessage().contains("Successful"));
  }

}