    package com.example.SpringBatchPartition.config;

    import com.example.SpringBatchPartition.DepartmentPartitioner;
    import com.example.SpringBatchPartition.DepartmentStepExecutionAggregator;
    import org.springframework.batch.core.Job;
    import org.springframework.batch.core.Step;
    import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
    import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
    import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
    import org.springframework.batch.core.launch.support.RunIdIncrementer;
    import org.springframework.batch.core.partition.support.Partitioner;
    import org.springframework.batch.core.partition.support.StepExecutionAggregator;
    import org.springframework.batch.core.scope.context.StepContext;
    import org.springframework.batch.core.scope.context.StepSynchronizationManager;
    import org.springframework.batch.core.step.tasklet.Tasklet;
    import org.springframework.batch.repeat.RepeatStatus;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.context.annotation.Bean;
    import org.springframework.context.annotation.Configuration;
    import org.springframework.core.task.SimpleAsyncTaskExecutor;
    import org.springframework.core.task.TaskExecutor;

    @Configuration
    @EnableBatchProcessing
    public class BatchConfig {

        @Autowired
        private JobBuilderFactory jobBuilderFactory;

        @Autowired
        private StepBuilderFactory stepBuilderFactory;

        @Bean
        public Job partitionedJob(Partitioner partitioner, Step slaveStep) {
            return jobBuilderFactory.get("partitionedJob")
                    .incrementer(new RunIdIncrementer())
                    .start(masterStep(partitioner, slaveStep))
                    .build();
        }

        @Bean
        public Step masterStep(Partitioner partitioner, Step slaveStep) {
            return stepBuilderFactory.get("masterStep")
                    .partitioner(slaveStep.getName(), partitioner)
                    .step(slaveStep) // Provide the slave step here
                    .gridSize(5)
                    .taskExecutor(taskExecutor())
                    .aggregator(stepExecutionAggregator())
                    .build();
        }
        @Bean
        public Step slaveStep(StepBuilderFactory stepBuilderFactory) {
            return stepBuilderFactory.get("slaveStep")
                    .tasklet(slaveTasklet())
                    .build();
        }

        @Bean
        public Tasklet slaveTasklet() {
            return (contribution, chunkContext) -> {
                String department = chunkContext.getStepContext().getStepExecution().getExecutionContext().getString("department");
                System.out.println("Processing department: " + department);
                return RepeatStatus.FINISHED;
            };
        }


        @Bean
        public Partitioner departmentPartitioner() {
            return new DepartmentPartitioner();
        }

        @Bean
        public TaskExecutor taskExecutor() {
            return new SimpleAsyncTaskExecutor();
        }

        @Bean
        public DepartmentStepExecutionAggregator stepExecutionAggregator() {
            return new DepartmentStepExecutionAggregator();
        }
    }