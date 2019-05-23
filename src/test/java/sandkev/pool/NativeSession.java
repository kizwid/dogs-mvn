package sandkev.pool;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by kevsa on 23/05/2019.
 */
public class NativeSession implements Session {

    private final String id;
    private final AtomicInteger invocationCount;

    public NativeSession() {
        this.id = UUID.randomUUID().toString();
        this.invocationCount = new AtomicInteger();
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void close() throws Exception {

    }

    @Override
    public int getInvocationCount() {
        return invocationCount.get();
    }

    @Override
    public int submit() {
        return invocationCount.incrementAndGet();
    }

    @Override
    public void destroy() {
        System.out.println("Destroyed native session" + toString());
    }
}
