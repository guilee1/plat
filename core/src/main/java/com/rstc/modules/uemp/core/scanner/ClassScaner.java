package com.rstc.modules.uemp.core.scanner;


import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.BeanDefinitionStoreException;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.core.type.filter.TypeFilter;
import org.springframework.util.ClassUtils;

public class ClassScaner implements ResourceLoaderAware {
	
	private ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
	private final List<TypeFilter> includeFilters = new LinkedList<TypeFilter>();
	private final List<TypeFilter> excludeFilters = new LinkedList<TypeFilter>();
	private MetadataReaderFactory metadataReaderFactory = new CachingMetadataReaderFactory(
			this.resourcePatternResolver);

	public void setResourceLoader(ResourceLoader resourceLoader) {
		this.metadataReaderFactory = new CachingMetadataReaderFactory(
				resourceLoader);
	}

	public final ResourceLoader getResourceLoader() {
		return this.resourcePatternResolver;
	}

	public void addIncludeFilter(TypeFilter includeFilter) {
		this.includeFilters.add(includeFilter);
	}

	public void addExcludeFilter(TypeFilter excludeFilter) {
		this.excludeFilters.add(0, excludeFilter);
	}

	public void resetFilters(boolean useDefaultFilters) {
		this.includeFilters.clear();
		this.excludeFilters.clear();
	}

	@SuppressWarnings("unchecked")
	public Set<Class<?>> scan(String basePackage,Class<? extends Annotation> annotation){
		return scan(basePackage, new Class[]{annotation});
	}
	
	public Set<Class<?>> scan(String basePackage,
			Class<? extends Annotation>[] annotations) {
		for (Class<? extends Annotation> anno : annotations)
			addIncludeFilter(new AnnotationTypeFilter(anno));
		return doScan(basePackage);
	}

	public Set<Class<?>> scan(String[] basePackages,
			Class<? extends Annotation>[] annotations) {
		for (Class<? extends Annotation> anno : annotations)
			addIncludeFilter(new AnnotationTypeFilter(anno));
		Set<Class<?>> classes = new HashSet<Class<?>>();
		for (String s : basePackages)
			classes.addAll(doScan(s));
		return classes;
	}

	public Set<Class<?>> doScan(String basePackage) {
		Set<Class<?>> classes = new HashSet<Class<?>>();
		try {
			String pattern = "classpath*:"
					+ ClassUtils.convertClassNameToResourcePath(basePackage)
					+ "/**/*.class";

			Resource[] resources = this.resourcePatternResolver
					.getResources(pattern);

			for (Resource resource : resources)
				if (resource.isReadable()) {
					MetadataReader reader = this.metadataReaderFactory.getMetadataReader(resource);

					String className = reader.getClassMetadata().getClassName();
					if ((((this.includeFilters.size() != 0) || (this.excludeFilters
							.size() != 0))) && (!(matches(reader))))
						continue;
					try {
						classes.add(Class.forName(className));
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					}
				}
		} catch (IOException ex) {
			throw new BeanDefinitionStoreException(
					"I/O failure during classpath scanning", ex);
		}

		return classes;
	}

	protected boolean matches(MetadataReader metadataReader) throws IOException {
		for (TypeFilter tf : this.excludeFilters) {
			if (tf.match(metadataReader, this.metadataReaderFactory)) {
				return false;
			}
		}
		for (TypeFilter tf : this.includeFilters) {
			if (tf.match(metadataReader, this.metadataReaderFactory)) {
				return true;
			}
		}
		return false;
	}
}