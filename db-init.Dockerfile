# Use the official mssql-tools image
FROM mcr.microsoft.com/mssql-tools

# Copy the db.sql file into the container
COPY db/db.sql /db_scripts/db.sql 