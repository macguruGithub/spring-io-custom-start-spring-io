# Copy the new jar to the build location
cp -f ./target/*.jar jenkins/build-img/

echo "****************************"
echo "** Building Docker Image ***"
echo "****************************"

cd ./jenkins/build-img/ && docker build -t $API_NAME:$BUILD_TAG --no-cache .
