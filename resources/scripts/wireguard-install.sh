#!/bin/bash

set -e

WG_INTERFACE="wg0"
WG_PORT="51820"
WG_SERVER_IP="172.16.16.1"
WG_CONFIG="/etc/wireguard/$WG_INTERFACE.conf"
CLIENT_NAME="client1"
CLIENT_CONFIG="/etc/wireguard/${CLIENT_NAME}.conf"

apt update && apt install -y wireguard

umask 077
wg genkey | tee /etc/wireguard/server_private.key | wg pubkey > /etc/wireguard/server_public.key
wg genkey | tee /etc/wireguard/${CLIENT_NAME}_private.key | wg pubkey > /etc/wireguard/${CLIENT_NAME}_public.key

SERVER_PRIVATE_KEY=$(cat /etc/wireguard/server_private.key)
CLIENT_PRIVATE_KEY=$(cat /etc/wireguard/${CLIENT_NAME}_private.key)
CLIENT_PUBLIC_KEY=$(cat /etc/wireguard/${CLIENT_NAME}_public.key)

cat > $WG_CONFIG <<EOF
[Interface]
Address = $WG_SERVER_IP
ListenPort = $WG_PORT
PrivateKey = $SERVER_PRIVATE_KEY
PostUp = iptables -A FORWARD -i $WG_INTERFACE -j ACCEPT; iptables -t nat -A POSTROUTING -o eth0 -j MASQUERADE
PostUp = sysctl -w -q net.ipv4.ip_forward=1
PostDown = iptables -D FORWARD -i $WG_INTERFACE -j ACCEPT; iptables -t nat -D POSTROUTING -o eth0 -j MASQUERADE
PostDown = sysctl -w -q net.ipv4.ip_forward=0
EOF