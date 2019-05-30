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
        int maxSize = 5;
        int maxInvocationCount = 15;
        SessionPool sessionPool = new SessionPool(maxSize, maxInvocationCount, 1, factory);

        assertEquals(0, sessionPool.getSize());

        //reuse the available session until it is spent
        for(int n = 1; n < maxInvocationCount + 1; n++){
            Session leasedSession = sessionPool.lease();

            verify(factory, times(1)).create();
            assertEquals(1, sessionPool.getSize());

            leasedSession.submit();
            assertEquals(n, nativeSession.getInvocationCount());

            leasedSession.close();
            if(n==maxInvocationCount){
                assertEquals(0, sessionPool.getSize());
            }else {
                assertEquals(1, sessionPool.getSize());
            }
        }

        //the next lease will add a fresh pooledSession
        nativeSession = new NativeSession();
        when(factory.create()).thenReturn(nativeSession);
        assertEquals(0, sessionPool.getSize());
        Session leasedSession = sessionPool.lease();
        verify(factory, times(2)).create();
        leasedSession.close();
        assertEquals(1, sessionPool.getSize());


    }

}