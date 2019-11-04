package ds.program.fvhr.baby.tools;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.persistence.Entity;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.NestableRuntimeException;
import org.hibernate.HibernateException;
import org.hibernate.cfg.AnnotationConfiguration;
import org.springframework.core.io.ClassPathResource;

public class AnnotationSessionFactoryBean extends
		org.springframework.orm.hibernate3.annotation.AnnotationSessionFactoryBean {

	private String[]	fAnnotatedDirs;

	public String[] getAnnotatedDirs() {
		return fAnnotatedDirs;
	}

	public void setAnnotatedDirs(String[] annotatedDirs) {
		fAnnotatedDirs = annotatedDirs;
	}

	private List<String> getClasses() {
		List<String> classes = new ArrayList<String>();

		for (String path : fAnnotatedDirs) {
			ClassPathResource res = new ClassPathResource(path);
			try {
				if (!res.getFile().isDirectory()) {
					throw new NestableRuntimeException("annotatedDir must be setting to a Directory" + res.getFile());
				} else {
					File[] clazzFiles = res.getFile().listFiles();
					String pkg = res.getPath().replaceAll("/", ".");
					for (File file : clazzFiles) {
						if (file.isFile() && file.getName().endsWith(".class")) {
							String clazz = pkg + "." + StringUtils.substringBefore(file.getName(), ".class");
							classes.add(clazz);
						} else {
							if (logger.isInfoEnabled()) {
								logger.info("filter " + file.getAbsoluteFile());
							}
						}
					}
				}
			} catch (IOException e) {
				throw new NestableRuntimeException("Directory Not Found:" + res.getPath());
			}
		}
		return classes;
	}

	@Override
	protected void postProcessAnnotationConfiguration(AnnotationConfiguration config) throws HibernateException {
		if (fAnnotatedDirs != null) {
			for (String clazz : getClasses()) {

				try {
					Class pc = Class.forName(clazz);
					
					if (pc.isAnnotationPresent(Entity.class)) {
						config.addAnnotatedClass(pc);
					}

					if (logger.isInfoEnabled()) {
						if (pc.isAnnotationPresent(Entity.class)) {
							logger.debug("add annotated class:" + clazz);
						} else {
							logger.info(clazz + " is not present " + Entity.class);
						}
					}

				} catch (ClassNotFoundException e) {
					e.printStackTrace();
					logger.error("add annotated class fail", e);
				}
			}
		}
	}
	
	@Override
	public void setAnnotatedClasses(Class[] annotatedClasses) {
		super.setAnnotatedClasses(annotatedClasses);
		for (Class klass : annotatedClasses) {
			logger.debug("add annotated class:" + klass);
		}
	}
}
