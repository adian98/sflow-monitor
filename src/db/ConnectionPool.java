package db;

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
            return ConnectionPool.newConnection("jdbc:sqlite:/tmp/db.db");
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

    private static Connection newConnection(String url) throws Exception {
        Connection conn =  DriverManager.getConnection(url);
        Statement stmt = conn.createStatement();
        DatabaseMetaData meta = conn.getMetaData();
        ResultSet tabs = meta.getTables(null, null, "host_description", null);
        if (!tabs.next()) {
            //create tables
            stmt.execute(HostDescription.schema());
            stmt.execute(HostCpuInfo.schema());
            stmt.execute(HostDiskIoInfo.schema());
            stmt.execute(HostMemoryInfo.schema());
            stmt.execute(HostNetIoInfo.schema());
        }
        return conn;
    }
}
