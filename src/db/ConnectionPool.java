package db;

import config.Config;
import counterrecord.*;

import java.sql.*;
import java.util.LinkedList;
import java.util.Queue;

public class ConnectionPool {
    int maxSize;
    Queue<Connection> connections;

    public ConnectionPool(int maxSize) {
        this.maxSize = maxSize;
        connections = new LinkedList<Connection>();
    }

    public synchronized Connection get() throws Exception {
        if (connections.size() > maxSize) {
            throw new Exception("db connections too large");
        }

        if (connections.isEmpty()) {
            return DriverManager.getConnection(Config.dbPath());
        } else {
            return connections.remove();
        }
    }

    public synchronized void put(Connection con) throws Exception {

        if (connections.size() > maxSize) {
            throw new Exception("db connections too large");
        } else {
            connections.offer(con);
        }
    }

    static public void initDb() throws  Exception {
        Connection conn = DriverManager.getConnection(Config.dbPath());
        Statement stmt = conn.createStatement();
        DatabaseMetaData meta = conn.getMetaData();
        ResultSet tabs = meta.getTables(null, null, "host_description", null);
        if (!tabs.next()) {
            //create tables
            stmt.execute(HostDescription.schema());
            stmt.execute(HostNetIoInfo.schema());
            stmt.execute(HostCpuInfo.schema());
            stmt.execute(HostDiskIoInfo.schema());
            stmt.execute(HostMemoryInfo.schema());

            stmt.execute(VirtDescription.schema());
            stmt.execute(VirtCpuInfo.schema());
            stmt.execute(VirtDiskIoInfo.schema());

        }
        conn.close();

        Config.LOG_INFO("init db success");

        HostDescription.loadFromDb();
        VirtDescription.loadFromDb();
    }
}
