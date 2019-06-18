package com.estyle.httpmock;

import com.estyle.httpmock.annotation.HttpMock;
import com.estyle.httpmock.common.AbstractHttpMockGenerator;
import com.estyle.httpmock.common.MockEntity;
import com.google.auto.service.AutoService;
import com.google.gson.Gson;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;

import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.HEAD;
import retrofit2.http.HTTP;
import retrofit2.http.OPTIONS;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;

@AutoService(Processor.class)
public class HttpMockProcessor extends AbstractProcessor {

    private Types mTypeUtils;
    private Elements mElementUtils;
    private Filer mFiler;
    private Messager mMessager;

    private void note(CharSequence note) {
        mMessager.printMessage(Diagnostic.Kind.NOTE, note);
    }

    private void error(CharSequence error) {
        mMessager.printMessage(Diagnostic.Kind.ERROR, error);
    }

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);

        mTypeUtils = processingEnv.getTypeUtils();
        mElementUtils = processingEnv.getElementUtils();
        mFiler = processingEnv.getFiler();
        mMessager = processingEnv.getMessager();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> annotationTypes = new LinkedHashSet<>();
        annotationTypes.add(HttpMock.class.getCanonicalName());
        return annotationTypes;
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        List<MockEntity> mockList = new ArrayList<>();

        // 获取全部@Mock信息
        Set<? extends Element> elements = roundEnvironment.getElementsAnnotatedWith(HttpMock.class);
        for (Element element : elements) {
            String url = null;

            GET get = element.getAnnotation(GET.class);
            if (get != null) {
                url = get.value();
            }
            POST post = element.getAnnotation(POST.class);
            if (post != null) {
                url = post.value();
            }
            HEAD head = element.getAnnotation(HEAD.class);
            if (head != null) {
                url = head.value();
            }
            PUT put = element.getAnnotation(PUT.class);
            if (put != null) {
                url = put.value();
            }
            DELETE delete = element.getAnnotation(DELETE.class);
            if (delete != null) {
                url = delete.value();
            }
            PATCH patch = element.getAnnotation(PATCH.class);
            if (patch != null) {
                url = patch.value();
            }
            OPTIONS options = element.getAnnotation(OPTIONS.class);
            if (options != null) {
                url = options.value();
            }
            HTTP http = element.getAnnotation(HTTP.class);
            if (http != null) {
                url = http.path();
            }


            HttpMock mock = element.getAnnotation(HttpMock.class);
            MockEntity mockEntity = new MockEntity();
            mockEntity.setDelayMillis(mock.delayMillis());
            mockEntity.setEnable(mock.enable());
            mockEntity.setFileName(mock.fileName());
            mockEntity.setUrl(url);
            mockList.add(mockEntity);
        }

        // 创建Java类，以JSON的格式保存全部@Mock信息
        String json = new Gson().toJson(mockList);
        MethodSpec getJSONString = MethodSpec.methodBuilder("getJSONString")
                .addAnnotation(Override.class)
                .addModifiers(Modifier.PUBLIC)
                .returns(String.class)
                .addStatement("return $S", json)
                .build();
        TypeSpec httpMockGenerator = TypeSpec.classBuilder("HttpMockGenerator")
                .superclass(AbstractHttpMockGenerator.class)
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                .addMethod(getJSONString)
                .build();
        JavaFile javaFile = JavaFile.builder("com.estyle.httpmock", httpMockGenerator)
                .addFileComment("This file was generated under the \"build\" folder and should not be edited.")
                .build();
        try {
            javaFile.writeTo(mFiler);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }
}
