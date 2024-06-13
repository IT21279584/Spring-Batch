
package com.example.SpringBatchPartition;
import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.item.ExecutionContext;

import java.util.HashMap;
import java.util.Map;

public class DepartmentPartitioner implements Partitioner {

    @Override
    public Map<String, ExecutionContext> partition(int gridSize) {
        Map<String, ExecutionContext> partitionMap = new HashMap<>();
        String[] departments = {"HR", "FINANCE", "IT", "SALES", "MARKETING"};

        for (String department : departments) {
            ExecutionContext context = new ExecutionContext();
            context.putString("department", department);
            partitionMap.put("partition_" + department, context);
        }

        return partitionMap;
    }
}
