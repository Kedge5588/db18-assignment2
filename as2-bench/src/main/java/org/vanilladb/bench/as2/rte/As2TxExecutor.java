package org.vanilladb.bench.as2.rte;

import org.vanilladb.bench.TxnResultSet;
import org.vanilladb.bench.as2.As2TransactionType;
import org.vanilladb.bench.as2.rte.jdbc.As2JdbcExecutor;
import org.vanilladb.bench.remote.SutConnection;
import org.vanilladb.bench.remote.SutResultSet;
import org.vanilladb.bench.rte.TransactionExecutor;
import org.vanilladb.bench.rte.jdbc.JdbcExecutor;

public class As2TxExecutor extends TransactionExecutor<As2TransactionType> {
	
	private As2JdbcExecutor jdbcExecutor = new As2JdbcExecutor();

	public As2TxExecutor(As2ParamGen pg) {
		this.pg = pg;
	}

	public TxnResultSet execute(SutConnection conn) {
		try {
			TxnResultSet rs = new TxnResultSet();
			rs.setTxnType(pg.getTxnType());

			// generate parameters
			Object[] params = pg.generateParameter();

			// send txn request and start measure txn response time
			long txnRT = System.nanoTime();
			
			SutResultSet result = executeTxn(conn, params);

			// measure txn response time
			txnRT = System.nanoTime() - txnRT;

			// display output
			if (TransactionExecutor.DISPLAY_RESULT)
				System.out.println(pg.getTxnType() + " " + result.outputMsg());

			rs.setTxnIsCommited(result.isCommitted());
			rs.setOutMsg(result.outputMsg());
			rs.setTxnResponseTimeNs(txnRT);
			rs.setTxnEndTime();

			return rs;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
	}
	
	@Override
	protected JdbcExecutor<As2TransactionType> getJdbcExecutor() {
		return jdbcExecutor;
	}
}
