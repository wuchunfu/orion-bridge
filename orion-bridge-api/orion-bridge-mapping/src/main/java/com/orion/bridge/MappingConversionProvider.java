package com.orion.bridge;

import com.orion.lang.support.Attempt;
import com.orion.lang.utils.reflect.PackageScanner;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

/**
 * 类型转换注册器
 *
 * @author Jiahang Li
 * @version 1.0.0
 * @since 2022/8/10 17:23
 */
@Component
@Slf4j
public class MappingConversionProvider implements InitializingBean {

    private final String CONVERSION_PACKAGE = this.getClass().getPackage().getName() + ".mapping.*";

    @Override
    public void afterPropertiesSet() throws Exception {
        new PackageScanner(CONVERSION_PACKAGE)
                .with(MappingConversionProvider.class)
                .scan()
                .getClasses()
                .forEach(Attempt.rethrows(s -> {
                    log.info("register type conversion {}", s.getName());
                    Class.forName(s.getName());
                }));
    }

}
