package botanical.harmony.kiwi;

import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicBoolean;

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
  void dispatching_command_with_registered_handler_instance_returns_successful_response() {
    TestCommandHandler handler = new TestCommandHandler(c -> {});
    DispatcherBuilder dispatcherBuilder = DispatcherBuilder.create();
    dispatcherBuilder.register(handler);
    Dispatcher dispatcher = dispatcherBuilder.build();
    TestCommand command = new TestCommand(42);

    CommandResponse response = dispatcher.dispatch(command);

    assertTrue(response.isSuccessful());
    assertTrue(response.getMessage().contains("Successful"));
  }

  @Test
  void dispatching_command_with_registered_handler_instance_successful_runs_handler() {
    AtomicBoolean invoked = new AtomicBoolean(false);
    TestCommandHandler handler = new TestCommandHandler(c -> invoked.set(true));
    DispatcherBuilder dispatcherBuilder = DispatcherBuilder.create();
    dispatcherBuilder.register(handler);
    Dispatcher dispatcher = dispatcherBuilder.build();
    TestCommand command = new TestCommand(42);

    dispatcher.dispatch(command);

    assertTrue(invoked.get());
  }
}