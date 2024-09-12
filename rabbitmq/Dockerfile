FROM rabbitmq:3.13.7-management

EXPOSE 5672
EXPOSE 15672

ADD rabbitmq.config /etc/rabbitmq/
ADD definitions.json /etc/rabbitmq/

RUN chown rabbitmq:rabbitmq /etc/rabbitmq/rabbitmq.config /etc/rabbitmq/definitions.json
