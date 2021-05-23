package jeventbus.kafka.consumer;

import jeventbus.streaming.EventMessage;
import org.apache.kafka.clients.consumer.KafkaConsumer;

public interface OffsetCommitStrategy {

    void prePoll(KafkaConsumer<String, EventMessage> consumer);

    void postProcess(KafkaConsumer<String, EventMessage> consumer);

    void postConsumer(KafkaConsumer<String, EventMessage> consumer);
}
