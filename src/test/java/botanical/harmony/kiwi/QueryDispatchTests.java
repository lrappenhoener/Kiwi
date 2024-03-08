package botanical.harmony.kiwi;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;

public class QueryDispatchTests {
  @Test
  void send_query_without_registered_handler_returns_not_successful_response() {
    DispatcherBuilder builder = DispatcherBuilder.create();
    Dispatcher dispatcher = builder.build();
    Query<List<String>> query = new TestQuery(new String[]{"foo", "bar"});

    QueryResponse<List<String>> response = dispatcher.send(query);

    assertFalse(response.isSuccessful());
  }
}
