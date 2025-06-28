package com.neto.posts.postservice.util;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TextUtilsTest {

    @Test
    void getFirstThreeSentencesSuccess() {
        // given
        final String text = """
        Artificial Intelligence (AI) is reshaping the Java software development landscape, 
        opening up new horizons for software engineers. From process automation to code optimization, 
        AI is creating breakthroughs in productivity and quality. Java, with its flexibility and rich 
        ecosystem, has become the pioneering platform for AI projects, thanks to its cross-platform 
        capabilities, comprehensive libraries, and large-scale data processing capabilities.
        2025 will see the emergence of 13 groundbreaking AI tools for Java developers. 
        These solutions include intelligent programming assistants, advanced machine learning frameworks, 
        and automated testing systems. They not only optimize the development process but also significantly 
        improve the reliability and security of Java applications. According to Gartner research, by 2025, 
        more than 75% of enterprises will move from the testing phase to deploying AI in production. 
        This trend has created a strong demand for AI-integrated Java applications, opening up new 
        opportunities and challenges for the developer community. They play a pivotal role in developing 
        intelligent applications such as chatbots, recommendation systems, and predictive analytics.Let's 
        explore in detail the transformation of AI in Java development, the top 13 AI tools, the guide 
        to building AI-integrated applications, and the future trends of the industry. This article will 
        provide a comprehensive view of how AI is reshaping the future of Java development and opening up 
        groundbreaking possibilities for developers.How AI is Transforming Java Development Artificial 
        Intelligence (AI) is transforming how software is crafted by automating repetitive activities, 
        offering context-aware code suggestions, and facilitating advanced data interpretation. 
        For Java developers, this innovation translates into: Increased Efficiency: AI-driven solutions 
        handle routine coding, enabling developers to tackle intricate challenges and foster nnovation. 
        Optimised Code Quality: AI tools detect errors, refine code structures, and ensure compliance with 
        established standards, yielding maintainable and dependable software. Accelerated Learning Curves: 
        AI-based coding assistants deliver instant feedback and context-relevant recommendations, 
        speeding up the adoption of new technologies and techniques. Java’s versatility 
        and robust ecosystem position it as a go-to language for AI projects: Cross-Platform Capability: 
        Java’s "write once, run anywhere" model ensures seamless functionality across diverse systems, 
        reducing overhead in deployment. Extensive Library Support: Tools such as Deeplearning4j 
        and Weka empower Java developers to engage with AI and machine learning frameworks effectively. 
        Scalability for Big Data: Java’s performance excels in building scalable applications, 
        ideal for handling complex data sets and computational demands inherent to AI. """;

        // when
        final String result = TextUtils.getFirstThreeSentences(text);

        // theb
        assertEquals("Artificial Intelligence (AI) is reshaping the Java software development landscape, " +
                "opening up new horizons for software engineers. From process automation to code optimization, " +
                "AI is creating breakthroughs in productivity and quality. Java, with its flexibility and " +
                "rich ecosystem, has become the pioneering platform for AI projects, thanks to its cross-platform " +
                "capabilities, comprehensive libraries, and large-scale data processing capabilities.", result);
    }

    @Test
    void getFirstThreeSentencesSuccess2() {
        final String text = """
            Artificial Intelligence (AI) is reshaping the Java software development landscape,
            opening up new horizons for software engineers. From process automation to code optimization,
            AI is creating breakthroughs in productivity and quality.

            Java, with its flexibility and rich ecosystem, has become the pioneering platform for AI projects,
            thanks to its cross-platform capabilities, comprehensive libraries, and large-scale data processing capabilities.
            """;

        final String result = TextUtils.getFirstThreeSentences(text);
        assertEquals("Artificial Intelligence (AI) is reshaping the Java software development landscape, " +
                "opening up new horizons for software engineers. From process automation to code optimization, " +
                "AI is creating breakthroughs in productivity and quality. Java, with its flexibility and rich " +
                "ecosystem, has become the pioneering platform for AI projects, thanks to its cross-platform " +
                "capabilities, comprehensive libraries, and large-scale data processing capabilities.", result);

    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"", " "})
    void getFirstThreeSentencesNullOrEmptyString(final String text) {
        final String result = TextUtils.getFirstThreeSentences(text);

        assertEquals("", result);
    }

}