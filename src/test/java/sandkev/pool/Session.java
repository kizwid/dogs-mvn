package sandkev.pool;

/**
 * A remote invocation session
 */
public interface Session extends AutoCloseable, Identifiable {
    int getInvocationCount();
    int submit();
    void destroy();
}
