package sandkev.pool;

import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by kevsa on 30/05/2019.
 */
public class SessionPoolTest {

    @Test
    public void canCreateSessionPool() throws Exception {
        NativeSession nativeSession = new NativeSession();
        NativeSessionFactory factory = mock(NativeSessionFactory.class);
        when(factory.create()).thenReturn(nativeSession);
        int maxSize = 10;
        SessionPool sessionPool = new SessionPool(maxSize, (maxSize * 2)+1, 1, factory);

        assertEquals(0, sessionPool.getSize());

        //create the 1st 10
        for(int n = 1; n < 11; n++){
            Session leasedSession = sessionPool.lease();

            verify(factory, times(Math.min(maxSize, n))).create();
            assertEquals(n, sessionPool.getSize());

            leasedSession.submit();
            assertEquals(n, nativeSession.getInvocationCount());

            leasedSession.close();
            assertEquals(n, sessionPool.getSize());
            //reset(factory);
        }

        //there after, recycle from the pool
        for(int n = 1; n < maxSize + 1; n++){
            Session leasedSession = sessionPool.lease();

            verify(factory, times(maxSize)).create();
            assertEquals(maxSize, sessionPool.getSize());

            leasedSession.submit();
            assertEquals(maxSize+n, nativeSession.getInvocationCount());

            leasedSession.close();
            assertEquals(maxSize, sessionPool.getSize());
            //reset(factory);
        }

        //then create again after 1st set expire due to max invocation


    }

}