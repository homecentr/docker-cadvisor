FROM gcr.io/google-containers/cadvisor:v0.36.0 as cadvisor

FROM homecentr/base:3.2.0-alpine

ENV CADVISOR_ARGS="-logtostderr"

RUN apk --no-cache add \
      libc6-compat=1.1.24-r9 \
      device-mapper=2.02.186-r1 \
      findutils=4.7.0-r0 \
      thin-provisioning-tools=0.7.1-r3

# Copy cAdvisor binaries
COPY --from=cadvisor /usr/bin/cadvisor /usr/bin/cadvisor

# Copy config customized in cAdvisor Docker image
COPY --from=cadvisor /etc/nsswitch.conf /etc/nsswitch.conf

# Copy S6 scripts
COPY ./fs/ /

HEALTHCHECK --interval=30s --timeout=10s --start-period=5s --retries=3 CMD wget --quiet --tries=1 --spider http://localhost:8080/healthz || exit 1

EXPOSE 8080

ENTRYPOINT [ "/init" ]