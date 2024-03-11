package botanical.harmony.kiwi;

import botanical.harmony.kiwi.helper.TestCommand;
import botanical.harmony.kiwi.helper.TestCommandHandler;
import botanical.harmony.kiwi.helper.TestProvider;
import botanical.harmony.kiwi.helper.ThrowingTestCommandHandler;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CommandDispatchTests {
  @Test
  void dispatching_command_without_registered_handler_instance_returns_failed_response() {
    DispatcherBuilder dispatcherBuilder = DispatcherBuilder.create();
    Dispatcher dispatcher = dispatcherBuilder.build();
    TestCommand command = new TestCommand(42);

    CommandResponse response = dispatcher.send(command);

    assertFalse(response.isSuccessful());
    assertTrue(response.getMessage().contains("no handler registered"));
  }

  @Test
  void async_dispatching_command_without_registered_handler_instance_returns_failed_response() {
    DispatcherBuilder dispatcherBuilder = DispatcherBuilder.create();
    Dispatcher dispatcher = dispatcherBuilder.build();
    TestCommand command = new TestCommand(42);

    CompletableFuture<CommandResponse> future = dispatcher.sendAsync(command);

    try {
      CommandResponse response = future.get();
      assertFalse(response.isSuccessful());
      assertTrue(response.getMessage().contains("no handler registered"));
    } catch (ExecutionException | InterruptedException e) {
      assertFalse(future.isCompletedExceptionally());
    }
  }

  @Test
  void dispatching_command_with_registered_handler_instance_returns_successful_response() {
    TestCommandHandler handler = new TestCommandHandler(c -> {
    });
    DispatcherBuilder dispatcherBuilder = DispatcherBuilder.create();
    dispatcherBuilder.registerCommandHandler(handler);
    Dispatcher dispatcher = dispatcherBuilder.build();
    TestCommand command = new TestCommand(42);

    CommandResponse response = dispatcher.send(command);

    assertTrue(response.isSuccessful());
    assertTrue(response.getMessage().contains("Successful"));
  }

  @Test
  void async_dispatching_command_with_registered_handler_instance_returns_successful_response() {
    TestCommandHandler handler = new TestCommandHandler(c -> {
    });
    DispatcherBuilder dispatcherBuilder = DispatcherBuilder.create();
    dispatcherBuilder.registerCommandHandler(handler);
    Dispatcher dispatcher = dispatcherBuilder.build();
    TestCommand command = new TestCommand(42);

    CompletableFuture<CommandResponse> future = dispatcher.sendAsync(command);

    try {
      CommandResponse response = future.get();
      assertTrue(response.isSuccessful());
      assertTrue(response.getMessage().contains("Successful"));
    } catch (ExecutionException | InterruptedException e) {
      assertFalse(future.isCompletedExceptionally());
    }
  }

  @Test
  void dispatching_command_with_registered_handler_class_returns_successful_response() {
    TestProvider testProvider = TestProvider.create();
    DispatcherBuilder dispatcherBuilder = DispatcherBuilder.create(testProvider);
    dispatcherBuilder.registerCommandHandler(TestCommandHandler.class);
    Dispatcher dispatcher = dispatcherBuilder.build();
    TestCommand command = new TestCommand(42);

    CommandResponse response = dispatcher.send(command);

    assertTrue(response.isSuccessful());
    assertTrue(response.getMessage().contains("Successful"));
  }

  @Test
  void async_dispatching_command_with_registered_handler_class_returns_successful_response() {
    TestProvider testProvider = TestProvider.create();
    DispatcherBuilder dispatcherBuilder = DispatcherBuilder.create(testProvider);
    dispatcherBuilder.registerCommandHandler(TestCommandHandler.class);
    Dispatcher dispatcher = dispatcherBuilder.build();
    TestCommand command = new TestCommand(42);

    CompletableFuture<CommandResponse> future = dispatcher.sendAsync(command);

    try {
      CommandResponse response = future.get();
      assertTrue(response.isSuccessful());
      assertTrue(response.getMessage().contains("Successful"));
    } catch (ExecutionException | InterruptedException e) {
      assertFalse(future.isCompletedExceptionally());
    }
  }

  @Test
  void dispatching_command_with_registered_handler_class_successful_invokes_handler() {
    AtomicBoolean invoked = new AtomicBoolean(false);
    TestProvider testProvider = new TestProvider();
    testProvider.registerCommandHandler(
            TestCommandHandler.class,
            () -> new TestCommandHandler(c -> {
              invoked.set(true);
            })
    );
    DispatcherBuilder dispatcherBuilder = DispatcherBuilder.create(testProvider);
    dispatcherBuilder.registerCommandHandler(TestCommandHandler.class);
    Dispatcher dispatcher = dispatcherBuilder.build();
    TestCommand command = new TestCommand(42);

    dispatcher.send(command);

    assertTrue(invoked.get());
  }

  @Test
  void async_dispatching_command_with_registered_handler_class_successful_invokes_handler() {
    AtomicBoolean invoked = new AtomicBoolean(false);
    TestProvider testProvider = new TestProvider();
    testProvider.registerCommandHandler(
            TestCommandHandler.class,
            () -> new TestCommandHandler(c -> {
              invoked.set(true);
            })
    );
    DispatcherBuilder dispatcherBuilder = DispatcherBuilder.create(testProvider);
    dispatcherBuilder.registerCommandHandler(TestCommandHandler.class);
    Dispatcher dispatcher = dispatcherBuilder.build();
    TestCommand command = new TestCommand(42);

    CompletableFuture<CommandResponse> future = dispatcher.sendAsync(command);
    try {
      future.get();
    } catch (ExecutionException | InterruptedException e) {
      assertFalse(future.isCompletedExceptionally());
    }

    assertTrue(invoked.get());
  }

  @Test
  void dispatching_command_with_registered_handler_instance_successful_invokes_handler() {
    AtomicBoolean invoked = new AtomicBoolean(false);
    TestCommandHandler handler = new TestCommandHandler(c -> invoked.set(true));
    DispatcherBuilder dispatcherBuilder = DispatcherBuilder.create();
    dispatcherBuilder.registerCommandHandler(handler);
    Dispatcher dispatcher = dispatcherBuilder.build();
    TestCommand command = new TestCommand(42);

    dispatcher.send(command);

    assertTrue(invoked.get());
  }

  @Test
  void async_dispatching_command_with_registered_handler_instance_successful_invokes_handler() {
    AtomicBoolean invoked = new AtomicBoolean(false);
    TestCommandHandler handler = new TestCommandHandler(c -> invoked.set(true));
    DispatcherBuilder dispatcherBuilder = DispatcherBuilder.create();
    dispatcherBuilder.registerCommandHandler(handler);
    Dispatcher dispatcher = dispatcherBuilder.build();
    TestCommand command = new TestCommand(42);

    CompletableFuture<CommandResponse> future = dispatcher.sendAsync(command);
    try {
      future.get();
    } catch (InterruptedException | ExecutionException e) {
      assertFalse(future.isCompletedExceptionally());
    }

    assertTrue(invoked.get());
  }

  @Test
  void async_dispatching_command_with_throwing_handler_instance_returns_failed_response() {
    TestCommandHandler handler = new TestCommandHandler(c -> {
      throw new RuntimeException();
    });
    DispatcherBuilder dispatcherBuilder = DispatcherBuilder.create();
    dispatcherBuilder.registerCommandHandler(handler);
    Dispatcher dispatcher = dispatcherBuilder.build();
    TestCommand command = new TestCommand(42);

    CompletableFuture<CommandResponse> future = dispatcher.sendAsync(command);
    try {
      CommandResponse response = future.get();
      assertFalse(response.isSuccessful());
    } catch (InterruptedException | ExecutionException e) {
      assertFalse(future.isCompletedExceptionally());
    }
  }

  @Test
  void dispatching_command_with_throwing_handler_instance_returns_failed_response() {
    TestCommandHandler handler = new TestCommandHandler(c -> {
      throw new RuntimeException();
    });
    DispatcherBuilder dispatcherBuilder = DispatcherBuilder.create();
    dispatcherBuilder.registerCommandHandler(handler);
    Dispatcher dispatcher = dispatcherBuilder.build();
    TestCommand command = new TestCommand(42);

    CommandResponse response = dispatcher.send(command);

    assertFalse(response.isSuccessful());
  }

  @Test
  void async_dispatching_command_with_throwing_handler_class_returns_failed_response() {
    TestProvider testProvider = TestProvider.create();
    DispatcherBuilder dispatcherBuilder = DispatcherBuilder.create(testProvider);
    dispatcherBuilder.registerCommandHandler(ThrowingTestCommandHandler.class);
    Dispatcher dispatcher = dispatcherBuilder.build();
    TestCommand command = new TestCommand(42);

    CompletableFuture<CommandResponse> future = dispatcher.sendAsync(command);
    try {
      CommandResponse response = future.get();
      assertFalse(response.isSuccessful());
    } catch (InterruptedException | ExecutionException e) {
      assertFalse(future.isCompletedExceptionally());
    }
  }

  @Test
  void dispatching_command_with_throwing_handler_class_returns_failed_response() {
    TestProvider testProvider = TestProvider.create();
    DispatcherBuilder dispatcherBuilder = DispatcherBuilder.create(testProvider);
    dispatcherBuilder.registerCommandHandler(ThrowingTestCommandHandler.class);
    Dispatcher dispatcher = dispatcherBuilder.build();
    TestCommand command = new TestCommand(42);

    CommandResponse response = dispatcher.send(command);

    assertFalse(response.isSuccessful());
  }
}