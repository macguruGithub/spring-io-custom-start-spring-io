

echo "********************"
echo "** Pushing image ***"
echo "********************"

IMAGE=$API_NAME

echo "** Logging in ***"
docker login -u $OCIR_USERNAME -p $OCIR_PASSWORD iad.ocir.io
echo "*** Tagging image ***"
docker tag $IMAGE:$BUILD_TAG $OCIR_REPOSITORY:$BUILD_TAG
echo "*** Pushing image ***"
docker push $OCIR_REPOSITORY:$BUILD_TAG

