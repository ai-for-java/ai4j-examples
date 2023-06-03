import dev.ai4j.chat.AiMessage;
import dev.ai4j.chat.ChatMessage;
import dev.ai4j.chat.UserMessage;
import dev.ai4j.flows.ChatFlow;
import dev.ai4j.model.chat.OpenAiChatModel;
import dev.ai4j.model.chat.SimpleChatHistory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.Duration;
import java.util.List;

import static dev.ai4j.chat.UserMessage.userMessage;
import static dev.ai4j.model.openai.OpenAiModelName.GPT_3_5_TURBO;

public class ChatExamples {

    public static class IfYouNeedSimplicity {

        public static void main(String[] args) throws IOException {

            ChatFlow chatFlow = ChatFlow.builder()
                    .chatModel(OpenAiChatModel.builder()
                            .modelName(GPT_3_5_TURBO)
                            .apiKey(System.getenv("OPENAI_API_KEY")) // https://platform.openai.com/account/api-keys
                            .build())
                    .chatHistory(SimpleChatHistory.builder()
                            .systemMessage("You are a helpful assistant.")
                            .capacityInTokens(100)
                            .build())
                    .build();

            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            while (true) {
                System.out.print("User: ");
                String userMessage = br.readLine();

                if ("exit".equalsIgnoreCase(userMessage)) {
                    return;
                }

                String messageFromAi = chatFlow.chat(userMessage);
                System.out.println("AI: " + messageFromAi);
            }
        }
    }

    public static class IfYouNeedMoreControl {

        public static void main(String[] args) {

            OpenAiChatModel openAiChatModel = OpenAiChatModel.builder()
                    .apiKey(System.getenv("OPENAI_API_KEY")) // https://platform.openai.com/account/api-keys
                    .modelName(GPT_3_5_TURBO)
                    .temperature(0.3)
                    .timeout(Duration.ofSeconds(120))
                    .build();

            SimpleChatHistory chatHistory = SimpleChatHistory.builder()
                    .systemMessage("You are a helpful assistant.")
                    .capacityInTokens(300)
                    .capacityInMessages(10)
                    .build();

            UserMessage userMessage = userMessage("Tell me a joke");

            // You have full control over the chat history.
            // You can decide if you want to add a particular message to the history.
            // You can process/modify the message before saving.
            chatHistory.add(userMessage);

            List<ChatMessage> history = chatHistory.history();

            AiMessage aiMessage = openAiChatModel.chat(history);

            // Here is the same as above. You have full control.
            chatHistory.add(aiMessage);

            System.out.println(aiMessage.contents());
        }
    }
}
