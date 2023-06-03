import dev.ai4j.StreamingResponseHandler;
import dev.ai4j.model.chat.OpenAiChatModel;

import java.util.List;

import static dev.ai4j.chat.UserMessage.userMessage;
import static dev.ai4j.model.openai.OpenAiModelName.GPT_3_5_TURBO;

public class StreamingExample {

    public static void main(String[] args) {

        OpenAiChatModel model = OpenAiChatModel.builder()
                .apiKey(System.getenv("OPENAI_API_KEY")) // https://platform.openai.com/account/api-keys
                .modelName(GPT_3_5_TURBO)
                .temperature(1.0)
                .build();

        model.chat(List.of(userMessage("Tell me a joke")), new StreamingResponseHandler() {

            @Override
            public void onPartialResponse(String partialResponse) {
                System.out.println("Partial response: '" + partialResponse + "'");
            }

            @Override
            public void onComplete() {
                System.out.println("Streaming completed");
            }

            @Override
            public void onError(Throwable error) {
                error.printStackTrace();
            }
        });
    }
}
