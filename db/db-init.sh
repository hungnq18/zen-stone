#!/bin/bash
# Wait 30 seconds for SQL Server to start up
sleep 30

# Run the setup script to create the DB and the schema in the DB
/opt/mssql-tools/bin/sqlcmd -S localhost -U sa -P "$SA_PASSWORD" -d master -i /docker-entrypoint-initdb.d/db.sql 