package botanical.harmony.kiwi.helper;

import botanical.harmony.kiwi.Query;

import java.util.List;

public class TestQuery implements Query<List<String>> {
  private final List<String> strings;

  public TestQuery(List<String> strings) {
    this.strings = strings;
  }

  public List<String> getStrings() {
    return strings;
  }
}
