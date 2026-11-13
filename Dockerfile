FROM ubuntu:latest
LABEL authors="ppedr"

ENTRYPOINT ["top", "-b"]