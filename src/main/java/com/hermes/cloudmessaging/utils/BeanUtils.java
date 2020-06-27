package com.hermes.cloudmessaging.utils;

import lombok.experimental.UtilityClass;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.beans.FeatureDescriptor;
import java.util.Arrays;

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
}
