package sandkev.pool;

/**
 * Created by kevsa on 23/05/2019.
 */
public class NativeSessionFactory {
    public Session create(){
        return new NativeSession();
    }
}
