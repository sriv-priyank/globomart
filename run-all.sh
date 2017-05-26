function note() {
    local GREEN NC
    GREEN='\033[0;32m'
    NC='\033[0m' # No Color
    printf "\n${GREEN}$@  ${NC}\n" >&2
}

set -e

cd support/discovery-server;            note "Building Discovery Server...";       ./gradlew clean build;
note "Running Discovery Server...";     nohup java -jar -Xms256m -Xmx256m build/libs/*-SNAPSHOT.jar &
cd -

cd support/api-gateway;                 note "Building API Gateway Server...";     ./gradlew clean build;
note "Running API Gateway Server...";   nohup java -jar -Xms256m -Xmx256m build/libs/*-SNAPSHOT.jar &
cd -

cd core/product-catalog-service;            note "Building Product Catalog Service...";     ./gradlew clean build;
note "Running Product Catalog Server...";   nohup java -jar -Xms256m -Xmx512m build/libs/*-SNAPSHOT.jar &
cd -

cd core/pricing-service;            note "Building Pricing Service...";     ./gradlew clean build;
note "Running Pricing Server...";   nohup java -jar -Xms256m -Xmx512m build/libs/*-SNAPSHOT.jar &
cd -

note "Navigate to http://localhost:8070/ for service dashboard";
