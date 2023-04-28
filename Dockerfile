FROM public.ecr.aws/amazoncorretto/amazoncorretto:11

COPY .WGC-API-0.0.1.jar /app.jar

ENTRYPOINT ["java","-jar","/app.jar"]