FROM openjdk:22-jdk
ADD target/MyWeb.jar MyWeb.jar
ENTRYPOINT [ "java", "-jar", "/MyWeb.jar" ]