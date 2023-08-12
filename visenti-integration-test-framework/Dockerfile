From docker-registry.cloud.visenti.com/maven:3.6.3-openjdk-8-slim
ARG ExecProfile
ARG ExecEnvironment
ARG ExecCustomer
RUN mkdir /opt/code
COPY . /opt/code
WORKDIR /opt/code
RUN echo "mvn clean test -P$ExecProfile -Denvironment=$ExecEnvironment -Dcustomer=$ExecCustomer" > /opt/run.sh
CMD ["sh", "/opt/run.sh"]