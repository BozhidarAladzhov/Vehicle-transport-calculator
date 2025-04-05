package com.example.vehicle_transport_calculator.config;



import com.example.vehicle_transport_calculator.service.JwtService;
import com.example.vehicle_transport_calculator.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.client.RestClient;

import java.util.Map;


@Configuration
public class RestConfig {

  @Bean("genericRestClient")
  public RestClient genericRestClient() {
    return RestClient.create();
  }

  @Bean("offersRestClient")
  public RestClient offersRestClient(OfferApiConfig offersApiConfig,
                                     ClientHttpRequestInterceptor requestInterceptor) {
    return RestClient
            .builder()
            .baseUrl(offersApiConfig.getBaseUrl())
            .defaultHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
            .requestInterceptor(requestInterceptor)
            .build();
  }

  @Bean
  public ClientHttpRequestInterceptor requestInterceptor(UserService userService,
                                                         JwtService jwtService) {
    return (r, b, e) -> {
      // put the logged user details into bearer token
      userService
              .getCurrentUser()
              .ifPresent(vtcUserDetails -> {
                String bearerToken = jwtService.generateToken(
                        vtcUserDetails.getUuid().toString(),//
                        Map.of(
                                "roles",
                                vtcUserDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList()
                        )
                );

                System.out.println("BEARER TOKEN: " + bearerToken);

                r.getHeaders().setBearerAuth(bearerToken);
              });

      return e.execute(r, b);
    };
  }

}
