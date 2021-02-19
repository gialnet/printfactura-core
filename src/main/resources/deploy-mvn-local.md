<!-- https://mvnrepository.com/artifact/com.lowagie/itext -->
<dependency>
    <groupId>com.lowagie</groupId>
    <artifactId>itext</artifactId>
    <version>2.1.7</version>
</dependency>

mvn spring-boot:build-image

>Connect with SSH Digital Ocean
Go to web panel
Settings->Security->Add ssh key

>login in the machine 
Create and open the ~/.ssh/authorized_keys file for editing using a terminal-based text editor
and copy the public key


mvn deploy:deploy-file -DgroupId=eu.europa.ec.markt.dss -DartifactId=dss-itext -Dversion=1.00 -Durl=file:local-maven-repo/ -DrepositoryId=local-
maven-repo -DupdateReleaseInfo=true -Dfile=local-maven-repo/dss-itext-1.00.jar


docker run -p 6379:6379 --name turedis -d redis redis-server --appendonly yes

-- doesn't work
docker run -it --network localhost --rm redis redis-cli -h turedis

diego uuid 2168839c-3d08-4411-8127-db678f89d055

luis@gmail.com 4a236c52-ea42-40c2-ba5e-23a2bb9522ba

mvn clean package spring-boot:repackage  -Dmaven.test.skip=true

openssl rsa -in printfactura.com_private_key.key  -check

openssl pkcs12 -export -in printfactura.com_private_key.key -out mykeystore.pkcs12 -name tomcat -noiter -nomaciter

openssl pkcs12 -export \
-in cert-chain.txt \
-inkey <private_key_filename> \
-name ‘tomcat’ \
-out keystore.p12

// this work
keytool -import -alias tomcat -file printfactura.com_ssl_certificate.cer -keystore keystore.p12 -storepass password

keytool -list -v -storetype pkcs12 -keystore keystore.p12


************* this is the working way with both certificates
openssl pkcs12 -export -in printfactura.com_ssl_certificate.cer -inkey printfactura.com_private_key.key -name tomcat -out keystore2.p12

**** exec linux

useradd vivaldi
passwd vivaldi
chown vivaldi:vivaldi core-0.0.1-SNAPSHOT.jar
chmod 500 core-0.0.1-SNAPSHOT.jar

ln -s /opt/vivaldi/core-0.0.1-SNAPSHOT.jar /etc/init.d/printfactura
service printfactura start

*******
nohup java -jar core-0.0.1-SNAPSHOT.jar &

mvn clean install -DskipTests

mvnw spring-boot:build-image -DskipTests  -Dspring-boot.build-image.imageName=redmoon/core-image

******* el plugin de spring boot parece que no funciona en Windows por el momento
--- esto si funciona
docker run -it -p443:443 redmoon/core-image:latest

docker build -t redmoon/core-image:latest .

-- visit documentation about this plugin
https://github.com/GoogleContainerTools/jib/tree/master/jib-maven-plugin

// in my local machine with Google JLIB
// create docker images little than others, no need docker
mvn compile jib:dockerBuild -Dimage=<docker redmoon/core:v1

docker run -it -p443:443 redmoon/core:v1