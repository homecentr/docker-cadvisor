FROM gcr.io/google-containers/cadvisor:v0.34.0 as cadvisor
FROM homecentr/base:1.0.0 as base

FROM alpine:3.11.3

ENV CADVISOR_ARGS="-logtostderr"

# Copy cAdvisor binaries
COPY --from=cadvisor / /

# Copy S6 overlay
COPY --from=base / /

# Copy S6 scripts
COPY ./fs/ /

HEALTHCHECK --interval=20s --timeout=10s --start-period=5s --retries=3 CMD wget --quiet --tries=1 --spider http://localhost:8080/healthz || exit 1

EXPOSE 8080

ENTRYPOINT [ "/init" ]