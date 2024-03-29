package com.uhire.rest.config;

import java.util.Collection;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.uhire.rest.CascadeSaveMongoEventListener;
import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;

@Configuration
@EnableEncryptableProperties
@EnableMongoRepositories(basePackages = "com.uhire.rest.repository")
public class MongoConfig extends AbstractMongoClientConfiguration {

	@Override
	protected String getDatabaseName() {
		return "jmscott";
	}

	@Value("${spring.data.mongodb.password}")
	protected String mongoPass;
	
	@Value("${spring.data.mongodb.uri}")
	protected String mongoUrl;
	
	private MongoClient mongoClient;
	
	@Override
	public MongoClient mongoClient() {
		final ConnectionString connectionString = new ConnectionString(mongoUrl);
		final MongoCredential mongoCredential = MongoCredential.createCredential("admin", "admin", mongoPass.toCharArray());
		
		final MongoClientSettings mongoClientSettings = MongoClientSettings.builder()
				.applyConnectionString(connectionString)
				.credential(mongoCredential)
				.build();
		this.mongoClient = MongoClients.create(mongoClientSettings);
		return this.mongoClient;
	}
	
	@Override
	public Collection<String> getMappingBasePackages() {
		return Collections.singleton("com.uhire");
	}
	
	@Bean
	public CascadeSaveMongoEventListener cascadingMongoEventListener() {
		return new CascadeSaveMongoEventListener();
	}
	
	@Bean
	public MongoTemplate mongoTemplate() {
		return new MongoTemplate(this.mongoClient, this.getDatabaseName());
	}	
}
