FROM mcr.microsoft.com/dotnet/sdk:8.0 AS build
WORKDIR /app
COPY zad3/ .
RUN dotnet restore
RUN dotnet publish -c Release -o out
FROM mcr.microsoft.com/dotnet/aspnet:8.0 AS runtime
WORKDIR /app
COPY --from=build /app/out ./
ENTRYPOINT ["dotnet", "zad3.dll"]
