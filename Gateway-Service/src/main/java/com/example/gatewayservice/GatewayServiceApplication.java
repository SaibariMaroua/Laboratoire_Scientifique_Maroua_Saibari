package com.example.gatewayservice;

import com.example.gatewayservice.configuration.RsaConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.ReactiveDiscoveryClient;
import org.springframework.cloud.gateway.discovery.DiscoveryClientRouteDefinitionLocator;
import org.springframework.cloud.gateway.discovery.DiscoveryLocatorProperties;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableConfigurationProperties(RsaConfig.class)
public class GatewayServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayServiceApplication.class, args);
    }
    @Bean(name = "customRouteDefinitionLocator")
    DiscoveryClientRouteDefinitionLocator routeDefinitionLocator
            (ReactiveDiscoveryClient rdc, DiscoveryLocatorProperties dlp){
        return new DiscoveryClientRouteDefinitionLocator(rdc,dlp);
    }
   /* Ce code définit un bean personnalisé nommé customRouteDefinitionLocator pour la configuration de routage dans Spring Cloud Gateway. Ce bean utilise un DiscoveryClientRouteDefinitionLocator, une classe spécialisée permettant de configurer le routage dynamique en fonction des services enregistrés dans un client de découverte (comme Eureka).

    Voici un détail sur chaque élément :

    Annotation @Bean(name = "customRouteDefinitionLocator") :

    L'annotation @Bean indique à Spring de gérer cette instance en tant que bean, afin qu'elle soit injectée là où elle est nécessaire dans l'application.
    name = "customRouteDefinitionLocator" donne un nom explicite au bean, pratique pour la gestion de plusieurs beans RouteDefinitionLocator dans le contexte Spring.
    Méthode routeDefinitionLocator :

    Cette méthode crée et retourne une instance de DiscoveryClientRouteDefinitionLocator.
    Ce locator utilise les informations d'un client de découverte pour définir dynamiquement des routes vers des services enregistrés, sans nécessiter de configuration manuelle pour chaque route.
    Paramètres ReactiveDiscoveryClient rdc et DiscoveryLocatorProperties dlp :

    ReactiveDiscoveryClient rdc : Ce client de découverte réactif communique avec le registre de services (comme Eureka) pour obtenir les informations sur les services disponibles.
    DiscoveryLocatorProperties dlp : Ce sont des propriétés de configuration utilisées pour personnaliser le comportement de DiscoveryClientRouteDefinitionLocator, comme le préfixe de routage.
            Fonctionnement :

    En utilisant ce bean, Spring Cloud Gateway peut obtenir des informations depuis le client de découverte pour créer des routes dynamiquement. Cela permet à chaque service enregistré (par exemple, un microservice géré par Eureka) d'être automatiquement routable sans intervention manuelle.
    En résumé
    Ce code configure un routage dynamique pour Spring Cloud Gateway, en utilisant les informations de services obtenues via un client de découverte (comme Eureka) et en définissant automatiquement des routes vers chaque service enregistré.*/
}
