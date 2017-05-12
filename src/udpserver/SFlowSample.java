package udpserver;

import java.sql.Connection;

public interface SFlowSample {
    public abstract void decode() throws Exception;

    public abstract void saveToDb(Connection conn) throws Exception;
}
