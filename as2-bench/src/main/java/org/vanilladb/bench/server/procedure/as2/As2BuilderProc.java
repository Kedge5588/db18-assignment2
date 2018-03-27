package org.vanilladb.bench.server.procedure.as2;

import org.vanilladb.bench.server.param.as2.As2SchemaBuilderProcParamHelper;
import org.vanilladb.bench.server.procedure.BasicStoredProcedure;
import org.vanilladb.core.server.VanillaDb;

public class As2BuilderProc extends BasicStoredProcedure<As2SchemaBuilderProcParamHelper> {

	public As2BuilderProc() {
		super(new As2SchemaBuilderProcParamHelper());
	}

	@Override
	protected void executeSql() {
		for (String cmd : paramHelper.getTableSchemas())
			VanillaDb.newPlanner().executeUpdate(cmd, tx);
		for (String cmd : paramHelper.getIndexSchemas())
			VanillaDb.newPlanner().executeUpdate(cmd, tx);
	}
}