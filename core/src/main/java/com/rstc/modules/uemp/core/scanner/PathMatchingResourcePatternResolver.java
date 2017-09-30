package com.rstc.modules.uemp.core.scanner;


import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.JarURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.UrlResource;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.Assert;
import org.springframework.util.PathMatcher;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;

import com.rstc.modules.uemp.core.log.Logger;


public class PathMatchingResourcePatternResolver implements ResourcePatternResolver {
	
	private static Method equinoxResolveMethod;
	private final ResourceLoader resourceLoader;
	private PathMatcher pathMatcher = new AntPathMatcher();

	public PathMatchingResourcePatternResolver() {
		this.resourceLoader = new DefaultResourceLoader();
	}

	public PathMatchingResourcePatternResolver(ClassLoader classLoader) {
		this.resourceLoader = new DefaultResourceLoader(classLoader);
	}

	public PathMatchingResourcePatternResolver(ResourceLoader resourceLoader) {
		Assert.notNull(resourceLoader, "ResourceLoader must not be null");
		this.resourceLoader = resourceLoader;
	}

	protected Resource convertClassLoaderURL(URL url) {
		return new UrlResource(url);
	}

	protected String determineRootDir(String location) {
		int prefixEnd = location.indexOf(":") + 1;
		int rootDirEnd = location.length();

		while ((rootDirEnd > prefixEnd)
				&& (getPathMatcher().isPattern(location.substring(prefixEnd,
						rootDirEnd)))) {
			rootDirEnd = location.lastIndexOf(47, rootDirEnd - 2) + 1;
		}
		if (rootDirEnd == 0) {
			rootDirEnd = prefixEnd;
		}
		return location.substring(0, rootDirEnd);
	}

	protected Set<Resource> doFindMatchingFileSystemResources(File rootDir,
			String subPattern) throws IOException {
		Logger.debug("Looking for matching resources in directory tree ["
				+ rootDir.getPath() + "]");

		Set<File> matchingFiles = retrieveMatchingFiles(rootDir, subPattern);
		Set<Resource> result = new LinkedHashSet<Resource>(matchingFiles.size());
		for (File file2 : matchingFiles) {
			File file = file2;
			result.add(new FileSystemResource(file));
		}
		return result;
	}

	protected Set<Resource> doFindPathMatchingFileResources(
			Resource rootDirResource, String subPattern) throws IOException {
		File rootDir = null;
		try {
			rootDir = rootDirResource.getFile().getAbsoluteFile();
		} catch (IOException ex) {
			Logger.debug(
					"Cannot search for matching files underneath "
							+ rootDirResource
							+ " because it does not correspond to a directory in the file system",
					ex);

			return Collections.emptySet();
		}
		return doFindMatchingFileSystemResources(rootDir, subPattern);
	}

	protected Set<Resource> doFindPathMatchingJarResources(
			Resource rootDirResource, String subPattern) throws IOException {
		URLConnection con = rootDirResource.getURL().openConnection();
		JarFile jarFile = null;
		String jarFileUrl = null;
		String rootEntryPath = null;
		boolean newJarFile = false;

		if (con instanceof JarURLConnection) {
			JarURLConnection jarCon = (JarURLConnection) con;
			jarCon.setUseCaches(false);
			jarFile = jarCon.getJarFile();
			jarFileUrl = jarCon.getJarFileURL().toExternalForm();
			JarEntry jarEntry = jarCon.getJarEntry();
			rootEntryPath = (jarEntry != null) ? jarEntry.getName() : "";
		} else {
			String urlFile = rootDirResource.getURL().getFile();

			urlFile = urlFile.replaceAll(".jar/", ".jar!/");
			urlFile = urlFile.replaceAll(".war/", ".war!/");
			int separatorIndex = urlFile.indexOf("!/");

			if (separatorIndex != -1) {
				jarFileUrl = urlFile.substring(0, separatorIndex);
				rootEntryPath = urlFile.substring(separatorIndex
						+ "!/".length());

				jarFile = getJarFile(jarFileUrl);
			} else {
				jarFile = new JarFile(urlFile);
				jarFileUrl = urlFile;
				rootEntryPath = "";
			}
			newJarFile = true;
		}
		try {
			Logger.debug("Looking for matching resources in jar file ["
					+ jarFileUrl + "]");

			if ((!("".equals(rootEntryPath)))
					&& (!(rootEntryPath.endsWith("/")))) {
				rootEntryPath = rootEntryPath + "/";
			}
			Set<Resource> result = new LinkedHashSet<Resource>(8);
			Enumeration<JarEntry> entries = jarFile.entries();
			while (entries.hasMoreElements()) {
				JarEntry entry = (JarEntry) entries.nextElement();
				String entryPath = entry.getName();
				if (entryPath.startsWith(rootEntryPath)) {
					String relativePath = entryPath.substring(rootEntryPath
							.length());

					if (getPathMatcher().match(subPattern, relativePath)) {
						result.add(rootDirResource.createRelative(relativePath));
					}
				}

			}

//			entries = result;

			return result;
		} finally {
			if (newJarFile)
				jarFile.close();
		}
	}

	protected void doRetrieveMatchingFiles(String fullPattern, File dir,
			Set<File> result) throws IOException {
		Logger.debug("Searching directory [" + dir.getAbsolutePath()
				+ "] for files matching pattern [" + fullPattern + "]");

		File[] dirContents = dir.listFiles();
		if (dirContents == null) {
			throw new IOException("Could not retrieve contents of directory ["
					+ dir.getAbsolutePath() + "]");
		}

		for (File content : dirContents) {
			String currPath = StringUtils.replace(content.getAbsolutePath(),
					File.separator, "/");

			if ((content.isDirectory())
					&& (getPathMatcher()
							.matchStart(fullPattern, currPath + "/"))) {
				doRetrieveMatchingFiles(fullPattern, content, result);
			}
			if (getPathMatcher().match(fullPattern, currPath))
				result.add(content);
		}
	}

	protected Resource[] findAllClassPathResources(String location)throws IOException {
		String path = location;
		if (path.startsWith("/")) {
			path = path.substring(1);
		}
		Enumeration<URL> resourceUrls = getClassLoader().getResources(path);
		Logger.info(String.format("findAllClassPathResources in %s", path));
		Set<Resource> result = new LinkedHashSet<Resource>(16);
		while (resourceUrls.hasMoreElements()) {
			URL url = (URL) resourceUrls.nextElement();
			Logger.info(String.format("findAllClassPathResources in #%s # %s",path, url.getPath()));
			result.add(convertClassLoaderURL(url));
		}
		return ((Resource[]) result.toArray(new Resource[result.size()]));
	}

	protected Resource[] findPathMatchingResources(String locationPattern)throws IOException {
		String rootDirPath = determineRootDir(locationPattern);
		String subPattern = locationPattern.substring(rootDirPath.length());
		Resource[] rootDirResources = getResources(rootDirPath);
		Set<Resource> result = new LinkedHashSet<Resource>(16);
		for (Resource rootDirResource2 : rootDirResources) {
			Resource rootDirResource = resolveRootDirResource(rootDirResource2);
			if (isJarResource(rootDirResource)) {
				result.addAll(doFindPathMatchingJarResources(rootDirResource,
						subPattern));
			} else {
				result.addAll(doFindPathMatchingFileResources(rootDirResource,
						subPattern));
			}
		}

		Logger.debug("Resolved location pattern [" + locationPattern
				+ "] to resources " + result);

		return ((Resource[]) result.toArray(new Resource[result.size()]));
	}

	public ClassLoader getClassLoader() {
		return getResourceLoader().getClassLoader();
	}

	protected JarFile getJarFile(String jarFileUrl) throws IOException {
		if (jarFileUrl.startsWith("file:")) {
			try {
				return new JarFile(ResourceUtils.toURI(jarFileUrl)
						.getSchemeSpecificPart());
			} catch (URISyntaxException ex) {
				return new JarFile(jarFileUrl.substring("file:".length()));
			}
		}

		return new JarFile(jarFileUrl);
	}

	public PathMatcher getPathMatcher() {
		return this.pathMatcher;
	}

	public Resource getResource(String location) {
		return getResourceLoader().getResource(location);
	}

	public ResourceLoader getResourceLoader() {
		return this.resourceLoader;
	}

	public Resource[] getResources(String locationPattern) throws IOException {
		Assert.notNull(locationPattern, "Location pattern must not be null");
		if (locationPattern.startsWith("classpath*:")) {
			if (getPathMatcher().isPattern(
					locationPattern.substring("classpath*:".length()))) {
				return findPathMatchingResources(locationPattern);
			}

			return findAllClassPathResources(locationPattern
					.substring("classpath*:".length()));
		}

		int prefixEnd = locationPattern.indexOf(":") + 1;
		if (getPathMatcher().isPattern(locationPattern.substring(prefixEnd))) {
			return findPathMatchingResources(locationPattern);
		}

		return new Resource[] { getResourceLoader()
				.getResource(locationPattern) };
	}

	protected boolean isJarResource(Resource resource) throws IOException {
		return ResourceUtils.isJarURL(resource.getURL());
	}

	protected Resource resolveRootDirResource(Resource original)
			throws IOException {
		if (equinoxResolveMethod != null) {
			URL url = original.getURL();
			if (url.getProtocol().startsWith("bundle")) {
				return new UrlResource((URL) ReflectionUtils.invokeMethod(
						equinoxResolveMethod, null, new Object[] { url }));
			}
		}

		return original;
	}

	protected Set<File> retrieveMatchingFiles(File rootDir, String pattern)
			throws IOException {
		if (!(rootDir.isDirectory())) {
			throw new IllegalArgumentException("Resource path [" + rootDir
					+ "] does not denote a directory");
		}

		String fullPattern = StringUtils.replace(rootDir.getAbsolutePath(),
				File.separator, "/");

		if (!(pattern.startsWith("/"))) {
			fullPattern = fullPattern + "/";
		}
		fullPattern = fullPattern
				+ StringUtils.replace(pattern, File.separator, "/");

		Set<File> result = new LinkedHashSet<File>(8);
		doRetrieveMatchingFiles(fullPattern, rootDir, result);
		return result;
	}

	public void setPathMatcher(PathMatcher pathMatcher) {
		Assert.notNull(pathMatcher, "PathMatcher must not be null");
		this.pathMatcher = pathMatcher;
	}
}
