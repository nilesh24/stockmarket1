package com.jpmorgan.stocks.factory.impl;

import org.apache.log4j.Logger;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import com.jpmorgan.stocks.factory.SpringService;

/**
 * Implementation of the Spring Service.
 * 
 * @author Nilesh Mehta
 *
 */

public class SpringServiceImpl implements SpringService {

	/**
	 * Logger service for the class.
	 */
	private Logger logger = Logger.getLogger(SpringServiceImpl.class);

	/**
	 * Spring context files pattern defined for the Stocks application.
	 */
	private static final String SPRING_CONTEXT_FILE_NAME = "classpath*:*stocks-*-config.xml";

	/**
	 * Creates instance for spring service
	 */
	private static final SpringService INSTANCE = new SpringServiceImpl();

	/**
	 * Spring context object.
	 */
	private AbstractApplicationContext springContext = null;

	/**
	 * Constructor of the class. The main purpose of the constructor is to load
	 * the Spring context
	 */
	private SpringServiceImpl() {
		logger.info("Loading Spring Context");
		springContext = new ClassPathXmlApplicationContext(SPRING_CONTEXT_FILE_NAME);
		springContext.registerShutdownHook();
		logger.info("Spring Context created");
	}

	/**
	 * Returns the singleton instance of the spring services.
	 * 
	 * @return An object of the SpringService
	 */
	public static SpringService getInstance() {
		return INSTANCE;
	}

	
	/**
	 * Returns an object of the bean configured in the Spring context with the
	 * name <i>beanName</i> and representing for the class <i>objectClass</i>.
	 * 
	 * @param beanName
	 *            The id for the bean in the Spring context.
	 * @param objectClass
	 *            The class of the bean configured with the name <i>beanName</i>
	 *            in the Spring context.
	 * 
	 * @return Return an object corresponding to the bean configured in the
	 *         Spring context with the name <i>beanName</i>
	 */
	public <T> T getBean(String beanName, Class<T> objectClass) {
		return springContext.getBean(beanName, objectClass);
	}

	/**
	 * Return an object of the bean configured in the Spring context for the class
	 * <i>objectClass</i>.
	 * 
	 * @param objectClass
	 *            The class of the bean configured in the Spring context.
	 * 
	 * @return Return an object corresponding to the bean configured in the
	 *         Spring context represented for the class <i>objectClass</i>.
	 */
	public <T> T getBean(Class<T> objectClass) {
		return springContext.getBean(objectClass);
	}

}
