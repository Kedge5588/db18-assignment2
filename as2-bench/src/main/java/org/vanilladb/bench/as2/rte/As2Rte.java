package org.vanilladb.bench.as2.rte;

import org.vanilladb.bench.StatisticMgr;
import org.vanilladb.bench.as2.As2TransactionType;
import org.vanilladb.bench.remote.SutConnection;
import org.vanilladb.bench.rte.RemoteTerminalEmulator;

public class As2Rte extends RemoteTerminalEmulator<As2TransactionType> {
	
	private As2TxExecutor executor;

	public As2Rte(SutConnection conn, StatisticMgr statMgr) {
		super(conn, statMgr);
		executor = new As2TxExecutor(new As2ParamGen());
	}
	
	protected As2TransactionType getNextTxType() {
		return As2TransactionType.READ_ITEM;
	}
	
	protected As2TxExecutor getTxExeutor(As2TransactionType type) {
		return executor;
	}
}
