FROM maven:3-jdk-12 as maven
RUN useradd -ms /bin/bash minesweeper
RUN mkdir -p /home/minesweeper && mkdir -p /home/minesweeper/.m2
COPY . /home/minesweeper/minesweeper
RUN chown -R minesweeper:minesweeper /home/minesweeper && chmod -R 775 /home/minesweeper
USER minesweeper
WORKDIR /home/minesweeper/minesweeper
RUN mvn clean install


FROM openjdk:13-jdk-alpine

RUN adduser -u 10000 -S -h /home/10000 10000
WORKDIR /home/10000/minesweeper
COPY --chown=10000:10000 --from=maven /home/minesweeper/minesweeper/target/minesweeper-0.0.1-SNAPSHOT.jar .

RUN mkdir -p logs/ && chown -R 10000:10000 /home/10000 && chmod -R 775 /home/10000

USER 10000
EXPOSE 8090
ENV LANG="C.UTF-8"
CMD java -Djava.security.egd=file:/dev/./urandom -Djdk.tls.client.protocols=TLSv1.2 -jar minesweeper-0.0.1-SNAPSHOT.jar
