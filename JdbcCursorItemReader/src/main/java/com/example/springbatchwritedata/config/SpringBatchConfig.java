package com.example.springbatchwritedata.config;

import com.example.springbatchwritedata.entity.Student;
import com.example.springbatchwritedata.component.StudentItemProcessor;
import com.example.springbatchwritedata.component.StudentResultRowMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.annotation.*;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.FlowJobBuilder;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.builder.SimpleStepBuilder;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

import javax.sql.DataSource;


@EnableBatchProcessing
@Configuration
public class SpringBatchConfig {
//    private static final Logger logger = LoggerFactory.getLogger(SpringBatchConfig.class);
    public static Logger logger = LoggerFactory.getLogger(SpringBatchConfig.class);
    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private DataSource dataSource;

    @BeforeJob
    public void beforeJob(){
        logger.info("Before Job");
    }

    @AfterJob
    public void afterJob(){
        logger.info("After Job");
    }

    @BeforeStep
    public void beforeStep(){
        logger.info("Before Step");
    }

    @AfterStep
    public void afterStep(){
        logger.info("After Step");
    }

    @BeforeChunk
    public void beforeChunk(ChunkContext chunkContext){
        logger.info("Before Chunk {}",chunkContext.getStepContext().getStepName());
    }

    @AfterChunk
    public void afterChunk(ChunkContext chunkContext){
        logger.info("After Chunk {}", chunkContext.getStepContext().getStepName());
    }

    @AfterChunkError
    public void afterChunkError(ChunkContext chunkContext){
        logger.info("After Chunk Error {}", chunkContext.getStepContext().getStepName());
    }



    @BeforeRead
    public void beforeRead(){
        logger.info("Before Read");
    }

    @AfterRead
    public void afterRead(Object item){
        logger.info("After Read {}", item);
    }
    @OnReadError
    public void onReadError(Exception e){
        logger.info("OnReadError {}", e.getMessage());
    }

    @BeforeProcess
    public void beforeProcess(Object item){
        logger.info("BeforeProcess {}", item);
    }

    @AfterProcess
    public void afterProcess(Object item, Object result){
        logger.info("AfterProcess {} -> {}", item, result);
    }

    @OnProcessError
    public void onProcessError(Object item, Exception e){
        logger.info("OnProcessError {}=< {}", item, e.getMessage());
    }

    @BeforeWrite
    public void beforeWrite(Object item){
        logger.info("BeforeWrite {}", item);
    }

    @AfterWrite
    public void afterWrite(Object item){
        logger.info("AfterWrite {}", item);
    }

    @OnWriteError
    public void OnWriteError(Exception e, Object item){
        logger.info("On Write Error {} -> {}",item, e.getMessage());
    }

    @OnSkipInRead
    public void onSkipError(Exception e){
        logger.info("On Skip Error {}", e.getMessage());
    }

    @OnSkipInProcess
    public void onSkipProcess(Object item, Throwable throwable){
        logger.info("On Skip Process {} -> {}", item, throwable.getMessage());
    }

    @OnSkipInWrite
    public void onSkipWrite(Object item, Throwable throwable){
        logger.info("On Skip Write {} -> {}", item, throwable.getMessage());
    }

    @Bean
    public JdbcCursorItemReader<Student> reader() {
        try{
            JdbcCursorItemReader<Student> reader = new JdbcCursorItemReader<Student>();
            reader.setDataSource(dataSource);
            reader.setSql("SELECT id, email, firstname, lastname FROM student");
            logger.info("SQL RESULT ", reader.getSql());
            reader.setRowMapper(new StudentResultRowMapper());
            logger.info("Reader initialized: {}", reader);
            return reader;
        }catch(Exception e){
            e.printStackTrace();
            throw e;
        }
    }

//    @Bean
//    public ItemProcessor<Student, Student> processor() {
//        return item -> {
//            // Simulate processing error
//            if (item.getId() % 2 == 0) {
//                throw new DataIntegrityViolationException("Simulated process error");
//            }
//            return item;
//        };
//    }

    @Bean
    public FlatFileItemWriter<Student> writer() {
        FlatFileItemWriter<Student> writer = new FlatFileItemWriter<>();
        writer.setResource(new FileSystemResource("/home/hsudusinghe/Documents/test.csv"));
        writer.setLineAggregator(getDelimitedLineAggregator());
        DelimitedLineAggregator<Student> aggregator = new DelimitedLineAggregator<>();

        return writer;
    }

    private DelimitedLineAggregator<Student> getDelimitedLineAggregator(){
        BeanWrapperFieldExtractor<Student> beanWrapperFieldExtractor = new BeanWrapperFieldExtractor<Student>();
        beanWrapperFieldExtractor.setNames(new String[]{"id", "email", "firstname", "lastname"});

        DelimitedLineAggregator<Student> aggregator = new DelimitedLineAggregator<Student>();
        aggregator.setDelimiter(",");
        aggregator.setFieldExtractor(beanWrapperFieldExtractor);
        return aggregator;
    }

    @Bean
    public Step executeStep() {
        StepBuilder stepBuilder = stepBuilderFactory.get("executeStep");
        SimpleStepBuilder<Student, Student> simpleStepBuilder = stepBuilder.chunk(1);
        return simpleStepBuilder.reader(reader()).processor(processor()).writer(writer()).build();
    }

    @Bean
    public Job dbToCsvJob() {

        JobBuilder jobBuilder = jobBuilderFactory.get("dbToCsvJob");
        jobBuilder.incrementer(new RunIdIncrementer());
        FlowJobBuilder flowJobBuilder = jobBuilder.flow(executeStep()).end();
        Job job = flowJobBuilder.build();

        return job;
    }

    @Bean
    public StudentItemProcessor processor(){
        return new StudentItemProcessor();
    }
}
