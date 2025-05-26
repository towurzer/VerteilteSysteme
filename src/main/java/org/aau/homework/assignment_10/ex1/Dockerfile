# Specifies the base image for the build
FROM node:20-alpine

# Sets the working directory within the container's filesystem.
WORKDIR /app

# Copies the package.json and any package-lock.json files into the container.
COPY package*.json ./

# Executes the command to install the project's dependencies.
RUN npm install

# Transfers all remaining files from the host's current directory to the container's working directory.
COPY . .

# Declares that the container will listen on port 8080 at runtime.
EXPOSE 8080

# Defines the default command that will be executed when the container starts.
CMD ["npm", "start"]