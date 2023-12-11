#!/usr/bin/bash

#start docker
#docker run -itd -e POSTGRES_USER=anorisno -e POSTGRES_PASSWORD=GYAGG -p 5432:5432 -v /data:/var/lib/postgresql/data --name postgresql postgres

#start postgresql cli client  
PGPASSWORD=GYAGG psql -U anorisno postgresql://localhost:5432/sale_adviser



