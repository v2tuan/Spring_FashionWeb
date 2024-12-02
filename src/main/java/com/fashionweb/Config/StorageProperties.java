package com.fashionweb.Config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component  //?? ko có thì dòng dưới bị lỗi
@ConfigurationProperties("storage")
public class StorageProperties {

    private String location;

}
