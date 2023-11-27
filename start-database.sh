#!/usr/bin/bash

docker run -itd -e POSTGRES_USER=anorisno -e POSTGRES_PASSWORD=GYAGG -p 5432:5432 -v /data:/var/lib/postgresql/data --name postgresql postgres


