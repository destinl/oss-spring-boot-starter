package com.ls.ossdemo.config;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.ls.ossdemo.core.OssProperties;
import com.ls.ossdemo.core.OssTemplate;
import com.ls.ossdemo.server.OssTemplateImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Description:
 * @Author: ls
 * @Date: 2024/7/1017:14
 */
@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(OssProperties.class)
//@EnableConfigurationProperties(OssProperties.class)： 自动装配我们的配置类
public class OssAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    //@ConditionalOnMissingBean： 修饰bean的一个注解，当你的bean被注册之后，注册相同类型的bean，就不会成功，它会保证你的bean只有一个，
    //即你的实例只有一个。多个会报错。
    public AmazonS3 ossClient(OssProperties ossProperties) {
//        // 客户端配置，主要是全局的配置信息,有很多的配置，有的指定了默认值有的没有
//        ClientConfiguration clientConfiguration = new ClientConfiguration();
//        clientConfiguration.setMaxConnections(ossProperties.getMaxConnections());
//        // url以及region配置
//        AwsClientBuilder.EndpointConfiguration endpointConfiguration = new AwsClientBuilder.EndpointConfiguration(
//                ossProperties.getEndpoint(), ossProperties.getRegion());
//        // 凭证配置
//        AWSCredentials awsCredentials = new BasicAWSCredentials(ossProperties.getAccessKey(),
//                ossProperties.getSecretKey());
//        AWSCredentialsProvider awsCredentialsProvider = new AWSStaticCredentialsProvider(awsCredentials);
//        // build amazonS3Client客户端
//        return AmazonS3Client.builder().withEndpointConfiguration(endpointConfiguration)
//                .withClientConfiguration(clientConfiguration).withCredentials(awsCredentialsProvider)
//                .disableChunkedEncoding().withPathStyleAccessEnabled(ossProperties.getPathStyleAccess()).build();
        AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials("此处修改为阿里云accessKeyId"
                        , "此处修改为accessKeySecret")))
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(
                        "此处修改为Endpoint",//https://oss-cn-guangzhou.aliyuncs.com
                        ""))
                .withPathStyleAccessEnabled(false)
                .withChunkedEncodingDisabled(true)
                .build();
        return s3Client;
    }

    @Bean
    @ConditionalOnBean(AmazonS3.class)
    //@ConditionalOnBean(AmazonS3.class)： 当给定的在bean存在时,则实例化当前Bean。
    public OssTemplate ossTemplate(AmazonS3 amazonS3) {
        return new OssTemplateImpl(amazonS3);
    }
}
