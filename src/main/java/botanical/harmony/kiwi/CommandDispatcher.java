package botanical.harmony.kiwi;

import java.util.Optional;

class CommandDispatcher {
  private final Handlers handlers;

  CommandDispatcher(Handlers<CommandHandler> handlers){
    this.handlers = handlers;
  }

  CommandResponse send(Command command){
    Optional<CommandHandler> optionalHandler = handlers.getBy(command);
    if (optionalHandler.isEmpty())
      return CommandResponse.failed("no handler registered");
    CommandHandler handler = optionalHandler.get();
    try {
      CommandResponse response = handler.handle(command);
      return response;
    }
    catch(Exception e){
      return CommandResponse.failed(e.toString());
    }
  }
}

