package chat.client.command;

import chat.client.exception.InterruptOperationException;

public interface Command {
    void execute() throws InterruptOperationException;
}
