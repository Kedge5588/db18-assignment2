package org.vanilladb.bench.server.procedure.as2;

import org.vanilladb.bench.as2.As2TransactionType;
import org.vanilladb.core.sql.storedprocedure.StoredProcedure;
import org.vanilladb.core.sql.storedprocedure.StoredProcedureFactory;

public class As2StoredProcFactory implements StoredProcedureFactory {

	@Override
	public StoredProcedure getStroredProcedure(int pid) {
		StoredProcedure sp;
		switch (As2TransactionType.fromProcedureId(pid)) {
		case SCHEMA_BUILDER:
			sp = new As2BuilderProc();
			break;
		case TESTBED_LOADER:
			sp = new As2TestbedLoaderProc();
			break;
		case READ_ITEM:
			sp = new As2ReadItemProc();
			break;
		default:
			sp = null;
		}
		return sp;
	}
}
