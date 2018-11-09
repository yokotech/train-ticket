FROM java:8


ADD ./target/ts-admin-route-service-1.0.jar /app/
CMD ["java", "-Xmx200m", "-Dcom.sun.management.jmxremote.port=12590", "-Dcom.sun.management.jmxremote.ssl=false", "-Dcom.sun.management.jmxremote.authenticate=false", "-jar", "/app/ts-admin-route-service-1.0.jar"]

EXPOSE 16113