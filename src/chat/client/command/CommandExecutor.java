package chat.client.command;

import chat.client.Operation;
import  chat.client.exception.InterruptOperationException;

import java.util.HashMap;
import java.util.Map;

public final class CommandExecutor {

    private CommandExecutor() {
    }

    static Map<Operation, Command> operationCommandMap = new HashMap<>();

    static {
//        operationCommandMap.put(Operation.INFO, new InfoCommand());
//        operationCommandMap.put(Operation.EXIT, new ExitCommand());
        operationCommandMap.put(Operation.LOGIN, new LoginCommand());
    }

    public static final void execute(Operation operation) throws InterruptOperationException {
        Command command = operationCommandMap.get(operation);
        command.execute();
    }
}