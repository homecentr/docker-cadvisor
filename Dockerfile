FROM gcr.io/google-containers/cadvisor:v0.36.0 as cadvisor

FROM homecentr/base:2.3.0-alpine

ENV CADVISOR_ARGS="-logtostderr"

RUN apk --no-cache add \
      libc6-compat=1.1.24-r2 \
      device-mapper=2.02.186-r0 \
      findutils=4.7.0-r0 && \
    apk --no-cache add thin-provisioning-tools=0.7.1-r3 --repository http://dl-3.alpinelinux.org/alpine/edge/main/

# Copy cAdvisor binaries
COPY --from=cadvisor /usr/bin/cadvisor /usr/bin/cadvisor

# Copy config customized in cAdvisor Docker image
COPY --from=cadvisor /etc/nsswitch.conf /etc/nsswitch.conf

# Copy S6 scripts
COPY ./fs/ /

HEALTHCHECK --interval=30s --timeout=10s --start-period=5s --retries=3 CMD wget --quiet --tries=1 --spider http://localhost:8080/healthz || exit 1

EXPOSE 8080

ENTRYPOINT [ "/init" ]