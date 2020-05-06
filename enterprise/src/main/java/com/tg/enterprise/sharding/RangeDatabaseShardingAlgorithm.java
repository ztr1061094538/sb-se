package com.tg.enterprise.sharding;

import com.google.common.collect.Range;
import io.shardingsphere.core.api.algorithm.sharding.RangeShardingValue;
import io.shardingsphere.core.api.algorithm.sharding.standard.RangeShardingAlgorithm;

import java.util.Collection;
import java.util.LinkedHashSet;

public class RangeDatabaseShardingAlgorithm implements RangeShardingAlgorithm<Long> {

	@Override
	public Collection<String> doSharding(Collection<String> availableTargetNames,
			RangeShardingValue<Long> shardingValue) {
		Collection<String> result = new LinkedHashSet<>(availableTargetNames.size());
		Range<Long> range = shardingValue.getValueRange();
		Integer lowerEndPoint = Integer.parseInt(String.valueOf(range.lowerEndpoint()).substring(0, 4));
		Integer upperEndpoint = Integer.parseInt(String.valueOf(range.upperEndpoint()).substring(0, 4));
		for (Integer value = lowerEndPoint; value <= upperEndpoint; value++) {
			String database_index = String.valueOf(value);
			for (String each : availableTargetNames) {
				if (each.endsWith(database_index)) {
					result.add(each);
				}
			}
		}
		if (result.isEmpty() && !availableTargetNames.isEmpty()) {
			result.add((String) availableTargetNames.toArray()[0]);
		}
		return result;
	}

}
