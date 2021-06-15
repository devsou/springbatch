package com.devsou.formation.springbatch;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.MultiResourceItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.sql.DataSource;

@Configuration
@EnableBatchProcessing
@EnableScheduling
public class BAtchConfigurations {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Value("classpath:input/users_input_*.csv")
    private Resource[] inputResources;

    @Bean
    public Job readCSVFilesJob() {
        return jobBuilderFactory.get("readCSVFilesJob").incrementer(new RunIdIncrementer()).start(step1()).build();
    }

    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1").<User, User>chunk(5)
                .reader(multiResourceItemReader())
                .listener(new MyItemReaderListener())
                //.writer(write())
                .writer(writerJDBC())
                .processor(processor())
                .build();
    }

    @Bean
    public ItemProcessor processor(){
        return new MyItemProcessor();
    }


    @Bean
    public MultiResourceItemReader<User> multiResourceItemReader() {
        MultiResourceItemReader<User> resourceItemReader = new MultiResourceItemReader<User>();
        resourceItemReader.setResources(inputResources);
        resourceItemReader.setDelegate(reader());
        resourceItemReader.setStrict(true);
        return resourceItemReader;
    }


    @Bean
    public FlatFileItemReader<User> reader(){
        FlatFileItemReader<User> reader = new FlatFileItemReader<>();
        //reader.setResource(new ClassPathResource("users_input_2.csv"));
        reader.setLinesToSkip(1);
        reader.setLineMapper(makeLineMapper());
        reader.setStrict(true);
        return reader;
    }


    private DefaultLineMapper makeLineMapper(){
        DefaultLineMapper mapper = new DefaultLineMapper();
        mapper.setLineTokenizer(makeLineTokenizer());
        mapper.setFieldSetMapper(makeFieldMapper());
        return mapper;
    }

    private DelimitedLineTokenizer makeLineTokenizer(){
        DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
        tokenizer.setDelimiter("|");
        tokenizer.setNames(new String[] { "id", "firstName", "lastName" });

        return tokenizer;
    }

    private BeanWrapperFieldSetMapper makeFieldMapper(){
        BeanWrapperFieldSetMapper mapper = new BeanWrapperFieldSetMapper();
        mapper.setTargetType(User.class);
        return mapper;
    }

    @Bean
    public ItemWriter<User> writerJDBC() {
        return new JdbcBatchItemWriterBuilder<User>()
                .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
                .sql("INSERT INTO USER (ID, FIRSTNAME, LASTNAME) VALUES (:id, :firstName, :lastName)")
                .dataSource(dataSource())
                .build();
    }

    @Bean
    public DataSource dataSource() {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setDriverClassName("org.h2.Driver");
        dataSource.setJdbcUrl("jdbc:h2:tcp://localhost/~/test");
        dataSource.setUsername("sa");
        dataSource.setPassword("");
        return dataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }


}
