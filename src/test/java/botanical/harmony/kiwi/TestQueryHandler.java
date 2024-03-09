package botanical.harmony.kiwi;

import java.util.List;

public class TestQueryHandler implements QueryHandler<TestQuery, List<String>>{
  @Override
  public QueryResponse<List<String>> handle(TestQuery testQuery) {
    return QueryResponse.success(testQuery.getStrings(), "Successful");
  }
}
