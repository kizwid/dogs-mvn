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
        private final LinkedBlockingQueue<PooledSession> pool;
        private final LinkedBlockingQueue<PooledSession> expired;
        private final Set<PooledSession> leased;
        private final long expiryTime;
        private final long maxInvocationCount;

        private PooledSession(Session session, LinkedBlockingQueue<PooledSession> pool, Set<PooledSession> leased, LinkedBlockingQueue<PooledSession> expired, long expiryTime, int maxInvocationCount) {
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
            } else if(System.currentTimeMillis() >= expiryTime){
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
    }

    private final LinkedBlockingQueue<PooledSession> pool = new LinkedBlockingQueue<>();
    private final LinkedBlockingQueue<PooledSession> expired = new LinkedBlockingQueue<>();
    private final Set<PooledSession> leased = Sets.newConcurrentHashSet();
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

    public Session lease() throws Exception {
        PooledSession session = pool.poll();
        if (session!=null){
            return validate(session);
        }else if(getSize() < maxSize){
            return createSession();
        }else {
            return takeAndValidate();
        }
    }

    private Session takeAndValidate() throws Exception {
        return validate(pool.take());
    }

    private Session validate(PooledSession session) throws Exception {
        if(System.currentTimeMillis() >= session.expiryTime){
            session.session.close();
            return lease();
        }else {
            leased.add(session);
            return session;
        }
    }

    private Session createSession() {
        return new PooledSession(factory.create(),
                pool, leased, expired,
                System.currentTimeMillis() +  TimeUnit.MINUTES.toMillis(timeToLiveMinutes),
                maxInvocationCount);
    }

    public int getSize() {
        return pool.size() + leased.size();
    }

}
