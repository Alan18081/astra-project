package com.alex.astraproject.shared;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(SharedContext.class)
@Configuration
public @interface EnableSharedModule {}
