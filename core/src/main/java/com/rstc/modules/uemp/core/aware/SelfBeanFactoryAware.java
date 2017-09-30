package com.rstc.modules.uemp.core.aware;

import org.springframework.beans.*;
import org.springframework.beans.factory.*;
import org.springframework.stereotype.Component;

/**
 * utility class for get spring bean by name.
 * @author Administrator
 *
 */
@Component
public final class SelfBeanFactoryAware implements BeanFactoryAware {

  private static BeanFactory beanFactory;  
  

  @Override
  public void setBeanFactory(BeanFactory factory) throws BeansException {  
        setBeanFactoryAware(factory);
  }  

  static void setBeanFactoryAware(BeanFactory factory) throws BeansException {
	  beanFactory = factory;
  }
  
  @SuppressWarnings("unchecked")
  public static <T> T getBean(String beanName) {  
      if (null != beanFactory) {  
          return (T) beanFactory.getBean(beanName);  
      }  
      return null;  
  }  
  
  public static BeanFactory getBeanFactory(){
	  return beanFactory;
  }

}
