package com.tg.enterprise.sharding;

import com.google.common.collect.Range;
import io.shardingsphere.core.api.algorithm.sharding.RangeShardingValue;
import io.shardingsphere.core.api.algorithm.sharding.standard.RangeShardingAlgorithm;

import java.util.Collection;
import java.util.HashSet;

public class RangeTableShardingAlgorithm implements RangeShardingAlgorithm<Long> {

	@Override
	public Collection<String> doSharding(Collection<String> availableTargetNames,
			RangeShardingValue<Long> shardingValue) {
		Collection<String> result = new HashSet<>(availableTargetNames.size());
		Range<Long> range = shardingValue.getValueRange();
		Integer lowerEndpoint = Integer.parseInt(String.valueOf(range.lowerEndpoint()).substring(0, 6));
		Integer upperEndpoint = Integer.parseInt(String.valueOf(range.upperEndpoint()).substring(0, 6));
		while (lowerEndpoint <= upperEndpoint || result.size() == 12)
		{
			String tableName = "";
			if (lowerEndpoint % 100 < 10) {
				tableName = "0";
			}
			tableName += String.valueOf(lowerEndpoint % 100);
			for (String each : availableTargetNames) 
			{
				if (each.endsWith(tableName)) {
					result.add(each);
				}
			}
			if (lowerEndpoint % 100 == 12)
			{
				lowerEndpoint = ((lowerEndpoint / 100 + 1)) * 100 + 1;//year+1 month = 1
			}
			else
			{
				lowerEndpoint = lowerEndpoint + 1;//month+1
			}
		}
		return result;
	}

}
