FROM eclipse-temurin:17-jdk-alpine as build
WORKDIR /vlpms

COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
COPY src src

RUN ./mvnw install -DskipTests
RUN mkdir -p target/dependency && (cd target/dependency; jar -xf ../*.jar)

FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp
ARG DEPENDENCY=/vlpms/target/dependency
COPY --from=build ${DEPENDENCY}/BOOT-INF/lib /vlpms/lib
COPY --from=build ${DEPENDENCY}/META-INF /vlpms/META-INF
COPY --from=build ${DEPENDENCY}/BOOT-INF/classes /vlpms
ENTRYPOINT ["java","-cp","vlpms:vlpms/lib/*","com.github.cb2222124.vlpms.backend.Application"]