package com.hermes.cloudmessaging.core.utils;

import lombok.experimental.UtilityClass;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.beans.FeatureDescriptor;
import java.beans.PropertyDescriptor;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

@UtilityClass
public class BeanUtils {

    public void copyNonNullProperties(Object source, Object target) {
        BeanWrapper beanWrapper = new BeanWrapperImpl(source);
        String[] nullProps =
                Arrays.stream(beanWrapper.getPropertyDescriptors()).filter(pd -> beanWrapper.getPropertyValue(pd.getName()) == null)
                        .map(FeatureDescriptor::getName)
                        .toArray(String[]::new);
        org.springframework.beans.BeanUtils.copyProperties(source, target, nullProps);
    }

    public Map<String, Object> getObjectPropertyValueMap(Object o) {
        BeanWrapper beanWrapper = new BeanWrapperImpl(o);

        return Arrays.stream(beanWrapper.getPropertyDescriptors()).filter(pd -> beanWrapper.getPropertyValue(pd.getName()) != null &&
                !"class".equals(pd.getName()))
                .collect(Collectors.toMap(PropertyDescriptor::getName, pd -> beanWrapper.getPropertyValue(pd.getName())));
    }
}
