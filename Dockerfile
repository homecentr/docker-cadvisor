FROM gcr.io/google-containers/cadvisor:v0.36.0 as cadvisor

FROM homecentr/base:2.0.0-alpine

ENV CADVISOR_ARGS="-logtostderr"

# Copy cAdvisor binaries
COPY --from=cadvisor / /

# Copy S6 scripts
COPY ./fs/ /

HEALTHCHECK --interval=20s --timeout=10s --start-period=5s --retries=3 CMD wget --quiet --tries=1 --spider http://localhost:8080/healthz || exit 1

EXPOSE 8080

ENTRYPOINT [ "/init" ]