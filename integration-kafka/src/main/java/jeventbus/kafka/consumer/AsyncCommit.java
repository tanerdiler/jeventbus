package jeventbus.kafka.consumer;

import jeventbus.streaming.EventMessage;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.clients.consumer.OffsetCommitCallback;
import org.apache.kafka.common.TopicPartition;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class AsyncCommit implements OffsetCommitStrategy {

    private AtomicInteger atomicInteger = new AtomicInteger(0);

    @Override
    public void prePoll(KafkaConsumer<String, EventMessage> consumer) {
        // do nothing
    }

    @Override
    public void postProcess(KafkaConsumer<String, EventMessage> consumer) {
        consumer.commitAsync(new OffsetCommitCallback() {

            private int marker = atomicInteger.incrementAndGet();

            public void onComplete(Map<TopicPartition,
                                OffsetAndMetadata> offsets, Exception exception) {

                if (exception != null) {
                    if (marker == atomicInteger.get()) {
                        consumer.commitAsync(this);
                    }
                } else {
                    //Cant' try anymore
                }

            } });
    }

    @Override
    public void postConsumer(KafkaConsumer<String, EventMessage> consumer) {
        // do nothing
    }
}
