FROM alpine:3.21.2

RUN apk add --no-cache \
    wireguard-tools \
    iproute2 \
    iptables \
    tinyproxy \
    bash \
    curl \
    openresolv \
    supervisor

RUN mkdir -p /etc/wireguard /etc/tinyproxy /var/log/supervisor

COPY supervisord.conf /etc/supervisor/supervisord.conf
COPY entrypoint.sh /entrypoint.sh

RUN chmod +x /entrypoint.sh

EXPOSE 8888
EXPOSE 51820/udp

ENTRYPOINT ["/entrypoint.sh"]