FROM postgres

COPY load-extensions.sql /docker-entrypoint-initdb.d/load-extensions.sql
COPY create-tables.sql /docker-entrypoint-initdb.d/create-tables.sql
COPY create-functions.sql /docker-entrypoint-initdb.d/create-functions.sql
COPY load-data.sql /docker-entrypoint-initdb.d/load-data.sql