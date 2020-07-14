#! /bin/bash

containerName=pg-quarkus-order

containerId=$(docker inspect -f '{{.Id}}' $containerName)

if [ -n "$containerId" ]; then
	docker start $containerName
else
	docker run -d --name $containerName \
		-v pg-quarkus-order:/var/lib/postgresql/data \
		-e POSTGRES_PASSWORD=p4ssw0rd \
		-e POSTGRES_USER=hello \
		-e POSTGRES_DB=order \
		-p 5432:5432 \
		postgres:12.2-alpine
fi