package com.sun.configuration.mybatis;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Description: 读取generatorConfig.xml, 生成对应的Bean, Dao, Mapper<br/>
 *
 * @author Sun
 * @date: 2016-12-27
 * @version v1.0
 */
public class AutoGenerator {
	public static void main(String[] args) throws Exception {

		List<String> warnings = new ArrayList<>();

		ConfigurationParser cp = new ConfigurationParser(warnings);
		File generatorConfigXml = new File("src/main/resources/generatorConfig.xml");
		Configuration config = cp.parseConfiguration(generatorConfigXml);

		boolean overwrite = true;
		DefaultShellCallback callback = new DefaultShellCallback(overwrite);

		MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
		myBatisGenerator.generate(null);
	}
}
