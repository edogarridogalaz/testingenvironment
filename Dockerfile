################# Build stage #################
FROM openjdk:11 as build
WORKDIR /workspace/app
COPY gradlew .
COPY gradle gradle
COPY build.gradle .
RUN apt-get install bash sed
COPY settings.gradle .
RUN chmod +x gradlew
RUN sed -i.bak 's/\r$//' gradlew
RUN sh gradlew -Dgradle.user.home=/workspace/app dependencies
COPY src src
RUN sh gradlew -Dgradle.user.home=/workspace/app build
RUN mkdir -p build/dependency && (cd build/dependency; jar -xf ../libs/*.jar)
################# Package stage #################
FROM alpine:latest
RUN apk add openjdk11
EXPOSE 8080
ARG DEPENDENCY=/workspace/app/build/dependency
COPY --from=build ${DEPENDENCY}/BOOT-INF/lib /app/app/lib
COPY --from=build ${DEPENDENCY}/META-INF /app/app/META-INF
COPY --from=build ${DEPENDENCY}/BOOT-INF/classes /app/app
WORKDIR /app
ENTRYPOINT java -cp "app:app/lib/*" "com.nubox.core.auth.register.RegisterApplication"