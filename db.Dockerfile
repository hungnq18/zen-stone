# This Dockerfile simply uses the official Microsoft SQL Server image as a base.
# It allows Render to treat the service as a buildable service, which enables attaching a persistent disk.
FROM mcr.microsoft.com/mssql/server:2019-latest 