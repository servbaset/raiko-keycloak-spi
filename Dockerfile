FROM jboss/keycloak:latest
COPY ./build/libs/raiko-spi-0.0.1.jar /opt/jboss/keycloak/standalone/deployments