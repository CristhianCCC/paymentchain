# Use official OpenJDK 17 image as the base image
FROM eclipse-temurin:17-alpine

# Create a new group with specific uid and non-root user called "admin"
# Be sure that group id are not present on host. if already exist change by arbitrary other uid
RUN addgroup -g 1028 devopsc \
    && adduser -D -G devopsc admin

# Create a new mount point at /tmp on native host because a volume is more efficient and faster than filesystem
VOLUME /tmp

# Copiamos el jar a la imagen
ARG JAR_FILE


COPY ${JAR_FILE} /tmp/app.jar


# Change ownership of the /app directory to the "admin" user
RUN chown -R admin:devopsc /tmp

# Switch to non-root "admin" user for added security
USER admin

# Ejecutamos el jar al iniciar el contenedor
#ENTRYPOINT ["java","-jar","/tmp/app.jar"]
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar /tmp/app.jar"]

#para crear la imagen desde la terminal de docker: docker build -t eureka_image --no-cache --build-arg JAR_FILE=target/eureka-0.0.1-SNAPSHOT.jar .
#para correr el contenedor en base a la imagen previamente creada docker run -p 7080:8080 --name eureka_container eureka_image:latest -d