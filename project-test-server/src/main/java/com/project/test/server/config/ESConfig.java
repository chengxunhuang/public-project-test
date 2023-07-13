package com.project.test.server.config;

import com.project.test.server.util.CharacterUtil;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.apache.http.nio.conn.ssl.SSLIOSessionStrategy;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.TrustStrategy;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.net.ssl.SSLContext;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.List;

/**
 * @Author: wh
 * @Date: 2022/06/16/14:55
 * @Description:
 */
@Configuration
public class ESConfig {

    @Value("${logging.elasticsearch.uris:}")
    private List<String> uris;
    @Value("${logging.elasticsearch.user:}")
    private String username;
    @Value("${logging.elasticsearch.password:}")
    private String password;

    @Bean
    public RestHighLevelClient restClient() {
        RestHighLevelClient restClient = null;
        try {
            final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
            credentialsProvider.setCredentials(AuthScope.ANY,
                    new UsernamePasswordCredentials(username, password));

            SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
                // 信任所有
                @Override
                public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                    return true;
                }
            }).build();
            SSLIOSessionStrategy sessionStrategy = new SSLIOSessionStrategy(sslContext, NoopHostnameVerifier.INSTANCE);

            HttpHost[] httpHosts = new HttpHost[uris.size()];
            for (int i = 0; i < uris.size(); i++) {
                String uri = uris.get(i);
                if (CharacterUtil.isBlank(uri) && !isURL(uri)) {
                    continue;
                }

                String scheme = uri.substring(0, uri.lastIndexOf("://"));
                String host = uri.substring(scheme.length() + 3, uri.lastIndexOf(":"));
                Integer port = Integer.valueOf(uri.substring(uri.lastIndexOf(":") + 1));

                httpHosts[i] = new HttpHost(host, port, scheme);
            }

            restClient = new RestHighLevelClient(
                RestClient.builder(httpHosts)
                    .setHttpClientConfigCallback(new RestClientBuilder.HttpClientConfigCallback() {
                        @Override
                        public HttpAsyncClientBuilder customizeHttpClient(HttpAsyncClientBuilder httpClientBuilder) {
                            httpClientBuilder.disableAuthCaching();
                            httpClientBuilder.setSSLStrategy(sessionStrategy);
                            httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
                            return httpClientBuilder;
                        }
                    }));
        } catch (Exception e) {
        }
        return restClient;
    }

    /**
     * 判断一个字符串是否为url
     * @param str String 字符串
     * @return boolean 是否为url
     * @author peng1 chen
     * **/
    public boolean isURL(String str) {
        // 转换为小写
        str = str.toLowerCase();
        String regex = "^((https|http)?://)"  //https、http
                + "?(([0-9a-z_!~*'().&=+$%-]+: )?[0-9a-z_!~*'().&=+$%-]+@)?" //ftp的user@
                + "(([0-9]{1,3}\\.){3}[0-9]{1,3}" // IP形式的URL- 例如：199.194.52.184
                + "|" // 允许IP和DOMAIN（域名）
                + "([0-9a-z_!~*'()-]+\\.)*" // 域名- www.
                + "([0-9a-z][0-9a-z-]{0,61})?[0-9a-z]\\." // 二级域名
                + "[a-z]{2,6})" // first level domain- .com or .museum
                + "(:[0-9]{1,5})?" // 端口号最大为65535,5位数
                + "((/?)|" // a slash isn't required if there is no file name
                + "(/[0-9a-z_!~*'().;?:@&=+$,%#-]+)+/?)$";
        return  str.matches(regex);
    }

}