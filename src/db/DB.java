package db;
import counter_record.*;
import log.LOG;

import java.sql.*;

public class DB {
    public static Connection db_conn;
    public static Object db_lock;
    public static String db_path;

    public static void initDb(String path) throws Exception {
        db_lock = "lock";
        db_path = path + "/sflow-monitor.db";

        db_conn = DriverManager.getConnection("jdbc:sqlite:" + db_path);
        db_conn.setAutoCommit(false);

        Statement stmt = db_conn.createStatement();

        //create tables
        stmt.execute(HostDescription.schema());
        stmt.execute(HostNetIoInfo.schema());
        stmt.execute(HostCpuInfo.schema());
        stmt.execute(HostDiskIoInfo.schema());
        stmt.execute(HostMemoryInfo.schema());

        stmt.execute(VirtDescription.schema());
        stmt.execute(VirtCpuInfo.schema());
        stmt.execute(VirtDiskIoInfo.schema());
        stmt.execute(VirtMemoryInfo.schema());
        stmt.execute(VirtNetIoInfo.schema());
        stmt.execute(HostNodeInfo.schema());

        stmt.close();
        db_conn.commit();

        LOG.INFO("init db success : %s", db_path);

        HostDescription.loadFromDb(db_conn);
        VirtDescription.loadFromDb(db_conn);
    }
}
