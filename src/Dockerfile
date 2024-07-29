FROM public.ecr.aws/amazoncorretto/amazoncorretto:17 AS build
VOLUME /tmp
COPY . .

# Remove todos os espa�os do mvnw
RUN sed -i 's/\r$//' mvnw

RUN chmod 755 ./mvnw
RUN ./mvnw clean install -DskipTests
WORKDIR /target
RUN mv *.jar /app.jar

EXPOSE 8013
ENTRYPOINT ["java","-jar","/app.jar","--spring.datasource.url=jdbc:postgresql://localhost:5432/desafio?currentSchema=public&"--spring.datasource.username=postgres","--spring.datasource.password=123456"]