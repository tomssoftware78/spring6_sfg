package be.tvde.di.lifecycle;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;

@Component
public class LifecycleDemoBean implements InitializingBean,
                                          DisposableBean,
                                          BeanNameAware,
                                          BeanFactoryAware,
                                          ApplicationContextAware,
                                          BeanPostProcessor {

   private String javaVer;

   public LifecycleDemoBean() {
      System.out.println("## I'm in the LifecycleBean Constructor");
   }

   @Value("${java.specification.version}")
   public void setJavaVer(final String javaVer) {
      this.javaVer = javaVer;
      System.out.println("## 1 Properties Set. Java Ver: r " + this.javaVer);
   }

   @Override
   public void setBeanName(final String name) {
      System.out.println("## 2 BeanNameAware.setBeanName() ...");
   }

   @Override
   public void setBeanFactory(final BeanFactory beanFactory) throws BeansException {
      System.out.println("## 3 BeanFactoryAware.setBeanFactory() ...");
   }

   @Override
   public void setApplicationContext(final ApplicationContext applicationContext) throws BeansException {
      System.out.println("## 4 ApplicationContextAware.setApplicationContext() ...");
   }

   @PostConstruct
   public void postConstruct() {
      System.out.println("## 5 @postConstruct ...");
   }

   @Override
   public void afterPropertiesSet() throws Exception {
      System.out.println("## 6 afterPropertiesSet Populates Properties... ");
   }

   @PreDestroy
   public void preDestroy() {
      System.out.println("## 7 @preDestroy ...");
   }

   @Override
   public void destroy() throws Exception {
      System.out.println("## 8 DisposableBean.destroy() ...");
   }
}
