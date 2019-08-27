// Utility functions
// Import this file into the pipeline using 'load'.

void sparseCheckout(String repositoryUrl, String branch, String workingDirectory, String contextDirectory) {
  echo "Performing a sparse checkout ..."
  echo "Url: ${repositoryUrl}"
  echo "Branch: ${branch}"
  echo "Context Directory: ${contextDirectory}"
  echo "Working Directory: ${workingDirectory}"

  checkout([
    $class: 'GitSCM',
    checkout: false,
    submoduleCfg: [],
    userRemoteConfigs: [[url: "${repositoryUrl}"]],
    branches: [[name: "*/${branch}"]],
    extensions: [
      [$class: 'RelativeTargetDirectory', relativeTargetDir: "${workingDirectory}"],
      [$class: 'SparseCheckoutPaths',  sparseCheckoutPaths:[[$class:'SparseCheckoutPath', path:"${contextDirectory}"]]],
      [$class: 'CloneOption', depth: 0, noTags: true, reference: '', shallow: true]
    ]
  ])
}

void build(String buildConfigName, int waitTimeout = 10, String contextDirectory = '', String envVars = '') {
  def buildFromDir = ''

  // Find all of the build configurations associated to the application ...
  def buildconfigs = openshift.selector("bc", "${buildConfigName}")
  echo "Found ${buildconfigs.count()} buildconfigs: ${buildconfigs.names()}"

  // Inject environment variables into the build as needed ...
  if (envVars?.trim()) {
    echo "Setting environment variables on bc/${buildConfigName} ..."
    echo "envVars: ${envVars}"
    buildconfigs.set("env bc/${buildConfigName} ${envVars}")
  }

  // Perform a binary build as needed ...
  if (contextDirectory?.trim()) {
    echo "Setting up for binary build using the source in the ${contextDirectory} directory ..."
    buildFromDir = "--from-dir='${contextDirectory}'"
    echo "buildFromDir: ${buildFromDir}"
  }

  // Kick off all the builds in parallel ...
  def builds = buildconfigs.startBuild("${buildFromDir}")
  echo "Started ${builds.count()} builds: ${builds.names()}"

  timeout(waitTimeout) {
    // Wait for all the builds to complete ...
    // This section will exit after the last build completes.
    echo "Waiting for builds to complete ..."
    builds.withEach {
      // untilEach and watch - do not support watching multiple named resources,
      // so we have to feed it one at a time.
      it.untilEach(1) {
          echo "${it.object().status.phase} - ${it.name()}"
          return (it.object().status.phase == "Complete")
      }
    }
  }

  echo "Builds complete ..."
}

// Get an image's hash tag
String getImageTagHash(String imageName, String tag = "") {

  if(!tag?.trim()) {
    tag = "latest"
  }

  def istag = openshift.raw("get istag ${imageName}:${tag} -o template --template='{{.image.dockerImageReference}}'")
  return istag.out.tokenize('@')[1].trim()
}

void deploy(String appName, String appSuffix, String namespace, String envTag) {
  openshift.withCluster() {
    openshift.withProject() {

      echo "Tagging ${appName} for deployment to ${envTag} ..."

      // Don't tag with BUILD_ID so the pruner can do it's job; it won't delete tagged images.
      // Tag the images for deployment based on the image's hash
      def IMAGE_HASH = utils.getImageTagHash("${appName}")
      echo "IMAGE_HASH: ${IMAGE_HASH}"
      openshift.tag("${appName}@${IMAGE_HASH}", "${appName}:${envTag}")
    }

    echo "Watching rollout of ${appName}${appSuffix} in ${namespace}-${envTag} ..."
    openshift.withProject("${namespace}-${envTag}") {
        def dc = openshift.selector('dc', "${appName}${appSuffix}")
        // Wait for the deployment to complete.
        // This will wait until the desired replicas are all available
        dc.rollout().status()
    }

    echo "Deployment Complete."
  }
}

return this;