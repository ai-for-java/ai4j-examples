import dev.ai4j.PromptTemplate;

import java.util.Map;

public class PromptTemplateExamples {

    static class PromptTemplateWithOneParameter {

        public static void main(String[] args) {

            PromptTemplate promptTemplate = PromptTemplate.from("Hi, my name is {{name}}.");

            String prompt = promptTemplate.format("name", "John");

            System.out.println(prompt);
        }
    }

    static class PromptTemplateWithMultipleParameters {

        public static void main(String[] args) {

            PromptTemplate promptTemplate = PromptTemplate.from("Hi, my name is {{name}}. I am {{age}} years old.");

            String prompt = promptTemplate.format(Map.of(
                    "name", "John",
                    "age", 35
            ));

            System.out.println(prompt);
        }
    }
}
