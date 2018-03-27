package org.vanilladb.bench.as2;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import org.vanilladb.bench.Benchmarker;
import org.vanilladb.bench.StatisticMgr;
import org.vanilladb.bench.TransactionType;
import org.vanilladb.bench.as2.rte.As2Rte;
import org.vanilladb.bench.remote.SutConnection;
import org.vanilladb.bench.remote.SutDriver;
import org.vanilladb.bench.rte.RemoteTerminalEmulator;

import org.vanilladb.bench.as2.As2Constants;

public class As2Benchmarker extends Benchmarker {
	private final String TABLES_DDL[] = {
			"CREATE TABLE item ( i_id INT, i_im_id INT, i_name VARCHAR(24), "
					+ "i_price DOUBLE, i_data VARCHAR(50) )" };
	private final String INDEXES_DDL[] = {
			"CREATE INDEX idx_item ON item (i_id)" };

	private final String TABLES_NAMES[] = { "item" };


	public As2Benchmarker(SutDriver sutDriver) {
		super(sutDriver);
	}

	public Set<TransactionType> getBenchmarkingTxTypes() {
		Set<TransactionType> txTypes = new HashSet<TransactionType>();
		for (TransactionType txType : As2TransactionType.values()) {
			if (txType.isBenchmarkingTx())
				txTypes.add(txType);
		}
		return txTypes;
	}

	protected void executeLoadingProcedure(SutConnection conn) throws SQLException {
		conn.callStoredProc(As2TransactionType.SCHEMA_BUILDER.ordinal());
		conn.callStoredProc(As2TransactionType.TESTBED_LOADER.ordinal());
	}

	// build schema and create table using JDBC
	protected void jdbcLoadTestBed(SutConnection conn) throws SQLException {
		String TABLES_DDL[] = { "CREATE TABLE item ( i_id INT, i_im_id INT, i_name VARCHAR(24), " + "i_price DOUBLE, i_data VARCHAR(50) )"};
		String INDEXES_DDL[] = {"CREATE INDEX idx_item ON item (i_id)"};
		String TABLES_NAMES[] = { "item" };

		Connection jdbcConn = conn.toJdbcConnection();

		try {
			// build schema
			Statement statement = jdbcConn.createStatement();
			statement.executeUpdate(TABLES_DDL);
			statement.executeUpdate(INDEXES_DDL);

			jdbcConn.commit();

			// insert item
			for (int i = 1; i <= As2Constants.NUM_ITEMS; i++) {
				iid = i;

				// Deterministic value generation by item id
				iimid = iid % (As2Constants.MAX_IM - As2Constants.MIN_IM) + As2Constants.MIN_IM;
				iname = String.format("%0" + As2Constants.MIN_I_NAME + "d", iid);
				iprice = (iid % (int) (As2Constants.MAX_PRICE - As2Constants.MIN_PRICE)) + As2Constants.MIN_PRICE;
				idata = String.format("%0" + As2Constants.MIN_I_DATA + "d", iid);

				sql = "INSERT INTO item(i_id, i_im_id, i_name, i_price, i_data) VALUES (" + iid + ", " + iimid + ", '"
						+ iname + "', " + iprice + ", '" + idata + "' )";

				statement.executeUpdate(sql);
			}

			jdbcConn.commit();
		} catch(Exception e) {
			System.out.println(e.toString())
		}
	}

	protected RemoteTerminalEmulator<As2TransactionType> createRte(SutConnection conn, StatisticMgr statMgr) {
		return new As2Rte(conn, statMgr);
	}
}
