# How to run

Start the docker container by entering following command in the terminal
```
docker run -d -p 8080:8080 vs-email-client:latest
```

Now the web service is started on http://localhost:8080


# Instruction

## How to Build Docker Image

Run command
```
docker build -t vs-email-client .
```

## Upload image to Dockerhub

Login with terminal
```
docker login
```

Find build image
```
docker image
```

Note the `IMAGE_ID`and tag the image

```
docker tag <IMAGE_ID> <YOUR_DOCKERHUB_USERNAME>/vs-email-client:latest
```

Push the image to Dockerhub
```
docker push <YOUR_DOCKERHUB_USERNAME>/vs-email-client:latest
```

Now the image can be found in the repositories on https://hub.docker.com/


