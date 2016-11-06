# ASI Challenge
## Assignment
> Your task is to write a HTTP API that, given a user's GitHub handle, retrieves the top five public repositories owned by that user, ordered by size (the GitHub API has a "size" field that you can use). You should return the data as JSON. Feel free to use the language and tools you are most comfortable in.

> Deploy your API behind a public facing URL so that we can test it. We would be keen to see the code and a brief description of how you deployed the API.
 

## Build & Deployment
Gradle build script produces an executable jar, which is packaged using Gradle Docker plugin into a Docker image. Any Docker hosting service should be able to run it, I am using http://sloppy.io.

The definition of the machine to be used for running the app is in the [sloppy.json config file](sloppy.json).

Sloppy.io does not provide a hosting for docker image, therefore I push the images to my DockerHub repository and sloppy.io fetches the images from there.
 
The release workflow would be as follows:
```
./gradlew distDocker # build the docker image
docker push tmilata/asi-challenge # push to docker repo

```

## Running Locally
`./gradlew distDocker && docker run -p 80:80 tmilata/asi-challenge` runs the app with port 80 of the docker machine forwarded to port 80 of your local machine.

## How To Use
`GET https://asi-challenge.sloppy.zone/user/<username>/repos` where `<username>` is a GitHub username.

## Try The Running Instance

https://asi-challenge.sloppy.zone/user/torvalds/repos

## Stack
 - Scala
 - Spray for HTTP client and server
 - Typesafe config for configuration
 - Gradle for building
 - DockerHub as Docker images repository
 - Sloppy.io as hosting