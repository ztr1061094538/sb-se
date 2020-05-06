package com.tg.enterprise.sharding;

import io.shardingsphere.core.api.algorithm.sharding.PreciseShardingValue;
import io.shardingsphere.core.api.algorithm.sharding.standard.PreciseShardingAlgorithm;

import java.util.Collection;

public final class PreciseTableShardingAlgorithm implements PreciseShardingAlgorithm<Long> {

	@Override
	public String doSharding(Collection<String> availableTargetNames, PreciseShardingValue<Long> shardingValue) {
		Long value = shardingValue.getValue();
		String tableName = String.valueOf(value).substring(4, 6);
		for (String each : availableTargetNames) {
			if (each.endsWith(tableName)) {
				return each;
			}
		}
		return null;
	}

}
