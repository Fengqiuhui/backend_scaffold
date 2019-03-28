package com.maoniunet.filter;

import com.baomidou.mybatisplus.toolkit.ArrayUtils;
import lombok.Data;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
public class IgnoreSessionBeanPostProcessor implements BeanPostProcessor {

    private Set<IgnoreSessionFilter.IgnoreSessionMapping> ignorePaths = IgnoreSessionFilter.IGNORE_PATHS;

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {

        Class<?> aClass = bean.getClass();
        RestController restController = AnnotationUtils.findAnnotation(aClass, RestController.class);
        if (restController != null) {

            List<Mapping> innerMappingList = new CopyOnWriteArrayList<>();

            IgnoreSession ignoreSessionController = AnnotationUtils.findAnnotation(aClass, IgnoreSession.class);

            Method[] methods = aClass.getMethods();
            for (Method method : methods) {
                RequestMapping requestMappingAnnotation = AnnotationUtils.findAnnotation(method, RequestMapping.class);
                if (ignoreSessionController != null && requestMappingAnnotation != null) {
                    innerMappingList.add(mapToInnerMapping(method, requestMappingAnnotation));
                } else {
                    IgnoreSession ignoreSessionMethod = AnnotationUtils.findAnnotation(method, IgnoreSession.class);
                    if (ignoreSessionMethod != null && requestMappingAnnotation != null) {
                        innerMappingList.add(mapToInnerMapping(method, requestMappingAnnotation));
                    }
                }
            }
            if (!CollectionUtils.isEmpty(innerMappingList)) {
                parseInnerMapping(aClass, innerMappingList);
            }
        }
        return bean;
    }

    private Mapping mapToInnerMapping(Method method, RequestMapping requestMappingAnnotation) {
        RequestMethod[] requestMethods = requestMappingAnnotation.method();
        GetMapping getMappingAnnotation = AnnotationUtils.findAnnotation(method, GetMapping.class);
        if (getMappingAnnotation != null) {
            return new InnerMapping(getMappingAnnotation.value(), requestMethods);
        }
        PostMapping postMappingAnnotation = AnnotationUtils.findAnnotation(method, PostMapping.class);
        if (postMappingAnnotation != null) {
            return new InnerMapping(postMappingAnnotation.value(), requestMethods);
        }
        PutMapping putMappingAnnotation = AnnotationUtils.findAnnotation(method, PutMapping.class);
        if (putMappingAnnotation != null) {
            return new InnerMapping(putMappingAnnotation.value(), requestMethods);
        }
        DeleteMapping deleteMappingAnnotation = AnnotationUtils.findAnnotation(method, DeleteMapping.class);
        if (deleteMappingAnnotation != null) {
            return new InnerMapping(deleteMappingAnnotation.value(), requestMethods);
        }
        return new InnerMapping(requestMappingAnnotation.value(), requestMethods);
    }

    private void parseInnerMapping(Class<?> aClass, List<Mapping> innerMappingList) {
        RequestMapping requestMappingController = AnnotationUtils.findAnnotation(aClass, RequestMapping.class);
        if (requestMappingController != null) {
            String[] classRequestPaths = requestMappingController.value();
            RequestMethod[] classRequestMethods = requestMappingController.method();
            SuperMapping superMapping = new SuperMapping(classRequestPaths, classRequestMethods);

            for (Mapping innerMapping : innerMappingList) {
                String[] paths = innerMapping.getPaths();
                RequestMethod[] requestMethods = innerMapping.getRequestMethods();
                if (ArrayUtils.isEmpty(paths)) {
                    addSuperMappingToIgnorePaths(superMapping, requestMethods);
                } else {
                    addInnerMappingToIgnorePaths(superMapping, new InnerMapping(paths, requestMethods));
                }
            }
        }
    }

    private void addSuperMappingToIgnorePaths(SuperMapping superMapping, RequestMethod[] requestMethods) {
        if (ArrayUtils.isEmpty(requestMethods)) {
            requestMethods = superMapping.getRequestMethods();
            if (ArrayUtils.isEmpty(requestMethods)) {
                requestMethods = new RequestMethod[]{RequestMethod.GET};
            }
        }
        for (RequestMethod requestMethod : requestMethods) {
            String[] paths = superMapping.getPaths();
            for (String path : paths) {
                ignorePaths.add(new IgnoreSessionFilter.IgnoreSessionMapping(path, requestMethod));
            }
        }
    }

    private void addInnerMappingToIgnorePaths(SuperMapping superMapping, InnerMapping innerMapping) {
        String[] innerPaths = innerMapping.getPaths();
        RequestMethod[] requestMethods = innerMapping.getRequestMethods();
        for (String path : innerPaths) {
            if (StringUtils.isEmpty(path)) {
                addSuperMappingToIgnorePaths(superMapping, requestMethods);
            } else {
                for (RequestMethod requestMethod : requestMethods) {
                    String[] superPaths = superMapping.getPaths();
                    for (String superPath : superPaths) {
                        if (superPath.endsWith("/")) {
                            superPath = superPath.substring(0, superPath.length() - 1);
                        }
                        if (path.startsWith("/")) {
                            path = path.substring(1);
                        }
                        ignorePaths.add(new IgnoreSessionFilter.IgnoreSessionMapping(superPath + "/" + path, requestMethod));
                    }
                }
            }
        }
    }

    @Data
    private static abstract class Mapping {
        protected String[] paths;
        protected RequestMethod[] requestMethods;
    }

    @Data
    private static class InnerMapping extends Mapping {

        public InnerMapping(String[] paths, RequestMethod[] requestMethods) {
            this.paths = paths;
            this.requestMethods = requestMethods;
        }
    }

    @Data
    private static class SuperMapping extends Mapping {

        public SuperMapping(String[] paths, RequestMethod[] requestMethods) {
            this.paths = paths;
            this.requestMethods = requestMethods;
        }
    }
}
