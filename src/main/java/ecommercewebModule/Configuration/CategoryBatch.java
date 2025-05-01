package ecommercewebModule.Configuration;

import ecommercewebModule.Entities.Category;
import ecommercewebModule.Repository.CategoryRepository;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

@Configuration
@EnableBatchProcessing
public class CategoryBatch {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    CategoryRepository categoryRepository;

    @Bean         // It is ItemReader to read the data from .csv file
    public FlatFileItemReader<Category> flatFileItemReader() {
        FlatFileItemReader<Category> itemReader = new FlatFileItemReader<>();
        itemReader.setResource(new FileSystemResource("src/main/resources/category.csv"));
        itemReader.setName("csvReader");
        itemReader.setLinesToSkip(1);
        itemReader.setLineMapper(lineMapper());
        return itemReader;
    }

    private LineMapper<Category> lineMapper() {
        DefaultLineMapper<Category> lineMapper = new DefaultLineMapper<>();

        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
        lineTokenizer.setDelimiter(",");
        lineTokenizer.setStrict(false);
        lineTokenizer.setNames("category_name", "description");

        BeanWrapperFieldSetMapper<Category> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(Category.class);

        lineMapper.setLineTokenizer(lineTokenizer);
        lineMapper.setFieldSetMapper(fieldSetMapper);

        return lineMapper;
    }


    @Bean
    public CategoryItemProcessor categoryItemProcessor() {
        return new CategoryItemProcessor();
    }


    @Bean
    public RepositoryItemWriter<Category> writer() {
        RepositoryItemWriter<Category> writer = new RepositoryItemWriter<>();
        writer.setRepository(categoryRepository);
        writer.setMethodName("save");
        return writer;
    }


    @Bean
    public Step step1() {
        return stepBuilderFactory.get("csc-step").<Category,Category>chunk(100)
                .reader(flatFileItemReader())
                .processor(categoryItemProcessor())
                .writer(writer())
                .build();
    }


    @Bean
    public Job job() {
        return jobBuilderFactory.get("importCategory")
                .flow(step1()).end().build();
    }


}
