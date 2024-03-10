package botanical.harmony.kiwi;


public interface CommandHandler<TCommand extends Command> {
  CommandResponse handle(TCommand command);
}

