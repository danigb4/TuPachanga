#!/bin/bash
# Script para preparar e iniciar el entorno Docker de Tupachanga

# Colores para mejor visualización
GREEN='\033[0;32m'
RED='\033[0;31m'
NC='\033[0m' # Sin color

echo -e "${GREEN}🔧 Cambiando permisos de los scripts de inicialización...${NC}"
if chmod -R 755 ./sql && chown -R 999:999 ./sql; then
  echo -e "${GREEN}✅ Permisos modificados correctamente.${NC}"
else
  echo -e "${RED}❌ Error al cambiar permisos o propietario.${NC}"
  exit 1
fi

echo -e "${GREEN}🛑 Deteniendo y eliminando contenedores existentes...${NC}"
if docker-compose down -v; then
  echo -e "${GREEN}✅ Contenedores detenidos y eliminados.${NC}"
else
  echo -e "${RED}❌ Error al detener o eliminar contenedores.${NC}"
  exit 1
fi

echo -e "${GREEN}🚀 Iniciando contenedores...${NC}"
if docker-compose up -d; then
  echo -e "${GREEN}✅ Contenedores iniciados exitosamente.${NC}"
else
  echo -e "${RED}❌ Error al iniciar los contenedores.${NC}"
  exit 1
fi

# Comprobación de que PostgreSQL está disponible
echo -e "${GREEN}🔍 Esperando que la base de datos esté disponible...${NC}"
TIMEOUT=30
while ! docker exec postgres_tupachanga_app pg_isready -U oretania > /dev/null 2>&1; do
  echo -n "."
  sleep 1
  TIMEOUT=$((TIMEOUT-1))
  if [ $TIMEOUT -le 0 ]; then
    echo -e "\n${RED}❌ La base de datos no se encuentra disponible tras 30 segundos.${NC}"
    exit 1
  fi
done

echo -e "\n${GREEN}✅ La base de datos PostgreSQL está disponible.${NC}"
