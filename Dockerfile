FROM jboss/keycloak:latest
COPY ./build/libs/raiko-spi-0.0.2.jar /opt/jboss/keycloak/standalone/deployments