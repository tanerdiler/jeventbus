package jeventbus.kafka.consumer;

import jeventbus.streaming.EventMessage;
import org.apache.kafka.clients.consumer.KafkaConsumer;

public class SyncCommit implements OffsetCommitStrategy {

    @Override
    public void prePoll(KafkaConsumer<String, EventMessage> consumer) {
        // do nothing
    }

    @Override
    public void postProcess(KafkaConsumer<String, EventMessage> consumer) {
        consumer.commitAsync();
    }

    @Override
    public void postConsumer(KafkaConsumer<String, EventMessage> consumer) {
        // do nothing
    }
}
