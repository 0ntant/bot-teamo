FROM opensearchproject/logstash-oss-with-opensearch-output-plugin:7.16.2


RUN rm -f /usr/share/logstash/pipeline/logstash.conf
COPY ./logstash.conf /usr/share/logstash/pipeline/logstash.conf
RUN cp /usr/share/logstash/vendor/bundle/jruby/2.5.0/gems/logstash-output-opensearch-1.2.0-java/lib/logstash/outputs/opensearch/templates/ecs-disabled/1x.json /usr/share/logstash/vendor/bundle/jruby/2.5.0/gems/logstash-output-opensearch-1.2.0-java/lib/logstash/outputs/opensearch/templates/ecs-disabled/2x.json
