FROM java:8


ADD ./target/ts-admin-travel-service-1.0.jar /app/
CMD ["java", "-Xmx200m", "-Djava.rmi.server.hostname=10.141.212.151", "-Dcom.sun.management.jmxremote", "-Dcom.sun.management.jmxremote.rmi.port=12590", "-Dcom.sun.management.jmxremote.port=12590", "-Dcom.sun.management.jmxremote.ssl=false", "-Dcom.sun.management.jmxremote.authenticate=false", "-jar", "/app/ts-admin-travel-service-1.0.jar"]

EXPOSE 16114