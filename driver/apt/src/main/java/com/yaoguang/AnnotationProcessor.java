package com.yaoguang;

import com.yaoguang.apt.ApiFactoryProcessor;
import com.yaoguang.apt.InstanceProcessor;
import com.yaoguang.lib.annotation.apt.ApiAppAnnotation;
import com.yaoguang.lib.annotation.apt.ApiDriverAnnotation;
import com.yaoguang.lib.annotation.apt.ApiMessageAnnotation;
import com.yaoguang.lib.annotation.apt.ApiOrderAnnotation;
import com.yaoguang.lib.annotation.apt.InstanceFactory;

import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;

@SupportedSourceVersion(SourceVersion.RELEASE_8)//java版本支持
@SupportedAnnotationTypes({//标注注解处理器支持的注解类型
        "com.yaoguang.lib.annotation.apt.ApiDriverAnnotation",
        "com.yaoguang.lib.annotation.apt.ApiOrderAnnotation",
        "com.yaoguang.lib.annotation.apt.ApiMessageAnnotation",
        "com.yaoguang.lib.annotation.apt.ApiAppAnnotation",
        "com.yaoguang.apt.InstanceProcessor"
})
public class AnnotationProcessor extends AbstractProcessor {

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        new ApiFactoryProcessor().process(processingEnv, roundEnv, this, ApiDriverAnnotation.class,"ApiDriverFactory", "ApiDriverFactory");
        new ApiFactoryProcessor().process(processingEnv, roundEnv, this, ApiOrderAnnotation.class,"ApiOrderFactory", "ApiOrderFactory");
        new ApiFactoryProcessor().process(processingEnv, roundEnv, this, ApiMessageAnnotation.class,"ApiMessageFactory", "ApiMessageFactory");
        new ApiFactoryProcessor().process(processingEnv, roundEnv, this, ApiAppAnnotation.class,"ApiAppFactory", "ApiAppFactory");
        new InstanceProcessor().process(processingEnv,roundEnv,this, InstanceFactory.class);
        return true;
    }
}
