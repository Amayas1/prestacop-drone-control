# build project and generate jar file
mvn clean install -DskipTests

# deploy to datalake our application
scp -P 2222 target/prestacop-drone-control-0.0.1-SNAPSHOT.jar  maria_dev@sandbox-hdp.hortonworks.com:clea

