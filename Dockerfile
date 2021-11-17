FROM jboss/keycloak:latest
COPY ./build/*.jar /opt/jboss/keycloak/standalone/deployments