package botanical.harmony.kiwi;


public interface CommandHandler<TCommand> {
  CommandResponse handle(TCommand command);
}

