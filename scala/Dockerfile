FROM bde2020/spark-submit:2.3.0-hadoop2.7

ENV SPARK_APPLICATION_JAR_NAME circle-datascience-etl-interview-1.0

COPY template.sh /

RUN apt-get-install apt-transport-https

RUN echo "deb https://dl.bintray.com/sbt/debian /" > /etc/apt/sources.list.d/sbt.list \
      && apt-key adv --keyserver hkp://keyserver.ubuntu.com:80 --recv 2EE0EA64E40A89B84B2DF73499E82A75642AC823

RUN apt-get update \
      && apt-get install -y maven \
      && chmod +x /template.sh \
      && update-java-alternatives -s java-1.8.0-openjdk-amd64 \
      && apt-get-install sbt \
      && mkdir -p /app \
      && mkdir -p /usr/src/app

WORKDIR /app

# Pre-install base libraries
ONBUILD COPY build.sbt /usr/src/app/
ONBUILD COPY plugins.sbt /usr/src/app/project/
ONBUILD RUN sbt update


# Copy the source code and build the application
ONBUILD COPY . /usr/src/app
ONBUILD RUN sbt clean assembly

CMD ["/bin/bash", "/template.sh"]
