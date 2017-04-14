package com.winsigns.investment.framework.integration;

import java.net.URI;
import java.net.URISyntaxException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.client.RestOperations;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * 服务间调用的基类
 * 
 * <p>
 * 注入了LoadBalancerClient和RestOperations
 * 
 * @author yimingjin
 * @since 0.0.1
 */
public class BaseIntegration {

  @Autowired
  protected RestOperations restTemplate;

  /**
   * 根据URL返回一个Json节点
   * <p>
   * 会抓取本次http的请求获取语言信息
   * 
   * @param url
   * @return
   * @since 0.0.4
   */
  protected ObjectNode getNode(String url) {
    RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
    Assert.notNull(requestAttributes);
    Assert.isInstanceOf(ServletRequestAttributes.class, requestAttributes);
    HttpServletRequest servletRequest = ((ServletRequestAttributes) requestAttributes).getRequest();
    Assert.notNull(servletRequest);

    HttpHeaders reponseHeaders = new HttpHeaders();
    reponseHeaders.set("accept-language", servletRequest.getHeader("accept-language"));
    try {
      RequestEntity<String> request =
          new RequestEntity<String>(reponseHeaders, HttpMethod.GET, new URI(url));
      ResponseEntity<ObjectNode> resultStr = restTemplate.exchange(request, ObjectNode.class);

      return resultStr.getBody();
    } catch (URISyntaxException e) {
      throw new RuntimeException(e);
    }
  }
}
