package com.jpmorgan.stocks.factory;

import com.jpmorgan.stocks.factory.impl.SpringServiceImpl;

/**
 * Spring service - It will load classes and services configured in the Spring
 * context.
 * 
 * @author Nilesh Mehta
 *
 */
public interface SpringService {

	/**
	 * Singleton instance of the SpringService.
	 */
	public final SpringService INSTANCE = SpringServiceImpl.getInstance();

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
	public <T extends Object> T getBean(String beanName, Class<T> objectClass);

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
	public <T extends Object> T getBean(Class<T> objectClass);

}
