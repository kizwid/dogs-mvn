package sandkev.pool;

import com.google.common.collect.Sets;

import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * used to manage a pool of sessions
 */
public class SessionPool {

    private static class PooledSession implements Session {

        private final Session session;
        private final LinkedBlockingQueue<Session> pool;
        private final LinkedBlockingQueue<Session> expired;
        private final Set<Session> leased;
        private final long expiryTime;
        private final long maxInvocationCount;

        public PooledSession(Session session, LinkedBlockingQueue<Session> pool, Set<Session> leased, LinkedBlockingQueue<Session> expired, long expiryTime, int maxInvocationCount) {
            this.session = session;
            this.leased = leased;
            this.expired = expired;
            this.pool = pool;
            this.expiryTime = expiryTime;
            this.maxInvocationCount = maxInvocationCount;
            this.leased.add(this);
        }

        @Override
        public String getId() {
            return session.getId();
        }

        @Override
        public void close() throws Exception {
            leased.remove(this);
            if(getInvocationCount() >= maxInvocationCount) {
                expired.offer(this);
            } else if(System.currentTimeMillis() < expiryTime){
                expired.offer(this);
            } else {
                pool.offer(this);
            }
        }

        @Override
        public int getInvocationCount() {
            return session.getInvocationCount();
        }

        @Override
        public int submit() {
            return session.submit();
        }

        @Override
        public void destroy() {
            session.destroy();
        }
    }


    private final LinkedBlockingQueue<Session> pool = new LinkedBlockingQueue<>();
    private final LinkedBlockingQueue<Session> expired = new LinkedBlockingQueue<>();
    private final Set<Session> leased = Sets.newConcurrentHashSet();
    private final int maxSize;
    private final int maxInvocationCount;
    private final int timeToLiveMinutes;
    private final NativeSessionFactory factory;

    public SessionPool(int maxSize, int maxInvocationCount, int timeToLiveMinutes, NativeSessionFactory factory) {
        this.maxSize = maxSize;
        this.maxInvocationCount = maxInvocationCount;
        this.timeToLiveMinutes = timeToLiveMinutes;
        this.factory = factory;
    }

    public Session lease() throws InterruptedException {

        if(pool.size() < maxSize){
            //add new items to the pool
            return new PooledSession(factory.create(),
                    pool, leased, expired,
                    System.currentTimeMillis() +  TimeUnit.MINUTES.toMillis(timeToLiveMinutes),
                    maxInvocationCount);
        }else {
            Session session = pool.take();
            leased.add(session);
            session.destroy();
            return session;
        }

    }

}
