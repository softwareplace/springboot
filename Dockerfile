FROM ghcr.io/graalvm/jdk-community:21

# Install required build tools, including xargs (findutils package)
RUN microdnf install -y make git zip unzip findutils && \
    microdnf clean all

# Create and set the workspace directory
RUN mkdir /workspace
WORKDIR /workspace

# Copy project files to the workspace
COPY . /workspace

# Set install script permissions if needed
RUN chmod +x /workspace/installer

# Set the command to run the install script
CMD ["make", "publish"]
