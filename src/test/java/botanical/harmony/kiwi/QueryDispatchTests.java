package botanical.harmony.kiwi;

import botanical.harmony.kiwi.helper.TestProvider;
import botanical.harmony.kiwi.helper.TestQuery;
import botanical.harmony.kiwi.helper.TestQueryHandler;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;

public class QueryDispatchTests {
  @Test
  void send_query_without_registered_handler_returns_failed_response() {
    DispatcherBuilder builder = DispatcherBuilder.create();
    Dispatcher dispatcher = builder.build();
    Query<List<String>> query = new TestQuery(List.of("foo", "bar"));

    QueryResponse<List<String>> response = dispatcher.send(query);

    assertFalse(response.isSuccessful());
  }

  @Test
  void send_async_query_without_registered_handler_returns_failed_response() {
    DispatcherBuilder builder = DispatcherBuilder.create();
    Dispatcher dispatcher = builder.build();
    Query<List<String>> query = new TestQuery(List.of("foo", "bar"));

    CompletableFuture<QueryResponse<List<String>>> future = dispatcher.sendAsync(query);
    try {
      QueryResponse<List<String>> response = future.get();
      assertFalse(response.isSuccessful());
    } catch (InterruptedException | ExecutionException e) {
      assertFalse(future.isCompletedExceptionally());
    }
  }

  @Test
  void send_query_with_registered_handler_instance_returns_successful_response() {
    DispatcherBuilder builder = DispatcherBuilder.create();
    builder.registerQueryHandler(new TestQueryHandler());
    Dispatcher dispatcher = builder.build();
    List<String> expectedResult = List.of("foo", "bar");
    Query<List<String>> query = new TestQuery(expectedResult);

    QueryResponse<List<String>> response = dispatcher.send(query);

    assertTrue(response.isSuccessful());
    assertTrue(response.getResult().isPresent());
    assertEquals(response.getResult().get(), expectedResult);
  }

  @Test
  void send_async_query_with_registered_handler_instance_returns_successful_response() {
    DispatcherBuilder builder = DispatcherBuilder.create();
    builder.registerQueryHandler(new TestQueryHandler());
    Dispatcher dispatcher = builder.build();
    List<String> expectedResult = List.of("foo", "bar");
    Query<List<String>> query = new TestQuery(expectedResult);

    CompletableFuture<QueryResponse<List<String>>> future = dispatcher.sendAsync(query);

    try {
      QueryResponse<List<String>> response = future.get();
      assertTrue(response.isSuccessful());
      assertTrue(response.getResult().isPresent());
      assertEquals(response.getResult().get(), expectedResult);
    } catch (ExecutionException | InterruptedException e) {
      assertFalse(future.isCompletedExceptionally());
    }
  }

  @Test
  void send_query_with_registered_handler_class_returns_successful_response() {
    TestProvider provider = TestProvider.create();
    DispatcherBuilder builder = DispatcherBuilder.create(provider);
    builder.registerQueryHandler(TestQueryHandler.class);
    Dispatcher dispatcher = builder.build();
    List<String> expectedResult = List.of("foo", "bar");
    Query<List<String>> query = new TestQuery(expectedResult);

    QueryResponse<List<String>> response = dispatcher.send(query);

    assertTrue(response.isSuccessful());
    assertTrue(response.getResult().isPresent());
    assertEquals(response.getResult().get(), expectedResult);
  }

  @Test
  void send_async_query_with_registered_handler_class_returns_successful_response() {
    TestProvider provider = TestProvider.create();
    DispatcherBuilder builder = DispatcherBuilder.create(provider);
    builder.registerQueryHandler(TestQueryHandler.class);
    Dispatcher dispatcher = builder.build();
    List<String> expectedResult = List.of("foo", "bar");
    Query<List<String>> query = new TestQuery(expectedResult);

    CompletableFuture<QueryResponse<List<String>>> future = dispatcher.sendAsync(query);

    try {
      QueryResponse<List<String>> response = future.get();

      assertTrue(response.isSuccessful());
      assertTrue(response.getResult().isPresent());
    } catch (ExecutionException | InterruptedException e) {
      assertFalse(future.isCompletedExceptionally());
    }
  }
}
