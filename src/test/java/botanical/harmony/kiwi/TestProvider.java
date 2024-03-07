package botanical.harmony.kiwi;

public class TestProvider implements HandlerProvider {
  public static TestProvider create() {
    return new TestProvider();
  }

  @Override
  public boolean canProvide(Class<?> clazz) {
    return true;
  }
}
