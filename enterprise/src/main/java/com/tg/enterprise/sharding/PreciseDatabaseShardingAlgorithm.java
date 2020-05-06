package com.tg.enterprise.sharding;

import io.shardingsphere.core.api.algorithm.sharding.PreciseShardingValue;
import io.shardingsphere.core.api.algorithm.sharding.standard.PreciseShardingAlgorithm;

import java.util.Collection;

public final class PreciseDatabaseShardingAlgorithm implements PreciseShardingAlgorithm<Long> {

	@Override
	public String doSharding(Collection<String> availableTargetNames, PreciseShardingValue<Long> shardingValue) {
		Long value = shardingValue.getValue();
		String tableName = String.valueOf(value).substring(0, 4);
		for (String each : availableTargetNames) {
			if (each.endsWith(tableName)) {
				return each;
			}
		}
		if (!availableTargetNames.isEmpty()) {
			return (String) availableTargetNames.toArray()[0];
		}
		return null;
	}
}
