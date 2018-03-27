package org.vanilladb.bench.as2;

import org.vanilladb.bench.TransactionType;

public enum As2TransactionType implements TransactionType {
	// Loading procedures
	SCHEMA_BUILDER, TESTBED_LOADER,
	
	// Main procedures
	READ_ITEM;
	
	public static As2TransactionType fromProcedureId(int pid) {
		return As2TransactionType.values()[pid];
	}
	
	public int getProcedureId() {
		return this.ordinal();
	}
	
	public boolean isBenchmarkingTx() {
		if (this == READ_ITEM)
			return true;
		return false;
	}
}
