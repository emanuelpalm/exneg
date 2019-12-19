package eu.arrowhead.proto.cosys.datasharing;

import eu.arrowhead.client.library.config.ApplicationInitListener;
import eu.arrowhead.common.CommonConstants;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@ComponentScan(basePackages = {CommonConstants.BASE_PACKAGE, DataProviderConstants.BASE_PACKAGE})
public class DataProducerMain extends ApplicationInitListener {

    public static void main(final String[] args) {
        SpringApplication.run(DataProducerMain.class, args);

    }
}

