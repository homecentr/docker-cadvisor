[![Project status](https://badgen.net/badge/project%20status/stable%20%26%20actively%20maintaned?color=green)](https://github.com/homecentr/docker-cadvisor/graphs/commit-activity) [![](https://badgen.net/github/label-issues/homecentr/docker-cadvisor/bug?label=open%20bugs&color=green)](https://github.com/homecentr/docker-cadvisor/labels/bug) [![](https://badgen.net/github/release/homecentr/docker-cadvisor)](https://hub.docker.com/repository/docker/homecentr/cadvisor)
[![](https://badgen.net/docker/pulls/homecentr/cadvisor)](https://hub.docker.com/repository/docker/homecentr/cadvisor) 
[![](https://badgen.net/docker/size/homecentr/cadvisor)](https://hub.docker.com/repository/docker/homecentr/cadvisor)

![CI/CD on master](https://github.com/homecentr/docker-cadvisor/workflows/CI/CD%20on%20master/badge.svg)
![Regular Docker image vulnerability scan](https://github.com/homecentr/docker-cadvisor/workflows/Regular%20Docker%20image%20vulnerability%20scan/badge.svg)



# HomeCentr - cAdvisor
This docker image is a repack of the original [cAdvisor](https://github.com/google/cadvisor) compliant with the HomeCenter docker images standard (S6 overlay, privilege drop etc.).

## Usage

```yml
version: "3.7"
services:
  cadvisor:
    build: .
    image: homecentr/cadvisor
    restart: unless-stopped
    ports:
      - 8080:8080
    volumes:
      - /var/run/docker.sock:/var/run/docker.sock:ro
      - /etc/machine-id:/etc/machine-id:ro
```

## Environment variables

| Name | Default value | Description |
|------|---------------|-------------|
| PUID | 7077 | UID of the user cadvisor should be running as. The UID must have sufficient rights to read from the Docker socket and the mounted directories (e.g. /var/lib/docker). |
| PGID | 7077 | GID of the user cadvisor should be running as. You must set the PUID if you want to set the PGID variable. |
| CADVISOR_ARGS | -logtostderr | Command line arguments to cadvisor executable. By default the logs are redirected to the container output |

## Exposed ports

| Port | Protocol | Description |
|------|------|-------------|
| 8080 | TCP | Metrics in Prometheus format |

## Volumes

Make sure you mount the Docker socket

## Security
The container is regularly scanned for vulnerabilities and updated. Further info can be found in the [Security tab](https://github.com/homecentr/docker-cadvisor/security).

### Container user
The container supports privilege drop. Even though the container starts as root, it will use the permissions only to perform the initial set up. The cadvisor process runs as UID/GID provided in the PUID and PGID environment variables.

:warning: Do not change the container user directly using the `user` Docker compose property or using the `--user` argument. This would break the privilege drop logic.

:bulb: To grant a user the permission to read Docker socket, you can add them to the docker group which is automatically created as a part of the Docker installation.