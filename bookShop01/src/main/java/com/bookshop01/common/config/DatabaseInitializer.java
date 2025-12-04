package com.bookshop01.common.config;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.stereotype.Component;

/**
 * On application startup, ensure the H2 file database has schema and seed data.
 * If the goods table is empty, apply schema.sql and data.sql from the classpath.
 */
@Component
public class DatabaseInitializer {
	@Autowired
	private DataSource dataSource;

	@PostConstruct
	public void initialize() {
		try (Connection connection = dataSource.getConnection();
		     Statement stmt = connection.createStatement()) {
			connection.setAutoCommit(false);
			boolean needsInit = false;
			try (ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM t_shopping_goods")) {
				if (rs.next()) {
					needsInit = rs.getInt(1) == 0;
				}
			} catch (Exception e) {
				// Table does not exist yet
				needsInit = true;
			}
			if (needsInit) {
				ScriptUtils.executeSqlScript(connection, new ClassPathResource("db/schema.sql"));
				ScriptUtils.executeSqlScript(connection, new ClassPathResource("db/data.sql"));
				connection.commit();
				System.out.println("[DatabaseInitializer] Schema and data loaded into H2 file database.");
			} else {
				connection.rollback();
			}
		} catch (Exception e) {
			System.out.println("[DatabaseInitializer] Initialization failed: " + e.getMessage());
		}
	}
}
