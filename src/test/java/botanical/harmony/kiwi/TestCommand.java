package botanical.harmony.kiwi;

public class TestCommand implements Command {
  private final int value;

  public TestCommand(int value) {

    this.value = value;
  }

  public int getValue() {
    return value;
  }
}
