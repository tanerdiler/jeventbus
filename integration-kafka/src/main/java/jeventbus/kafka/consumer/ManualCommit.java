package jeventbus.kafka.consumer;

import jeventbus.streaming.EventMessage;
import org.apache.kafka.clients.consumer.CommitFailedException;
import org.apache.kafka.clients.consumer.KafkaConsumer;

public class ManualCommit implements OffsetCommitStrategy {
    @Override
    public void prePoll(KafkaConsumer<String, EventMessage> consumer) {

    }

    @Override
    public void postProcess(KafkaConsumer<String, EventMessage>  consumer) {
        consumer.commitSync();
    }

    @Override
    public void postConsumer(KafkaConsumer<String, EventMessage> consumer) {

    }
}
