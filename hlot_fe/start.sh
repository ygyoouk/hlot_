#!/bin/sh

nginx -t -c /etc/nginx/nginx.conf
nginx -g "daemon off;"