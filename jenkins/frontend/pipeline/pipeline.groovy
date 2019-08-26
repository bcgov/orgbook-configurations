// Main Pipeline Steps
// Import this file into another pipeline using 'load'.

{ ->
  stage('Checkout Source') {
    script {

      echo "Cleaning out working directory ..."
      deleteDir()

      echo "Checking out the main indy-catalyst credential-registry client code ..."
      sparseCheckout("${SOURCE_REPO}", "${SOURCE_REPO_BRANCH}","${SOURCE_REPO_WORKING_DIRECTORY}", "${SOURCE_REPO_CONTEXT_DIRECTORY}")

      if (THEME_REPO) {
        echo "Checking out custom theme source code ..."
        sparseCheckout("${THEME_REPO}", "${THEME_REPO_BRANCH}","${THEME_REPO_WORKING_DIRECTORY}", "${THEME_REPO_CONTEXT_DIRECTORY}")

        echo "Copying the custom themes into the theme directory ..."
        sh (
          returnStdout: true,
          script: "cp -r ${THEME_REPO_WORKING_DIRECTORY}/${THEME_REPO_CONTEXT_DIRECTORY}* ${SOURCE_REPO_WORKING_DIRECTORY}/${SOURCE_REPO_THEME_DIRECTORY}"
        )
      }
    }
  }

  stage("Build ${ARTIFACTS_NAME} ...") {
    script {
      openshift.withCluster() {
        openshift.withProject() {
          echo "Building the application artifacts ..."
          build("${ARTIFACTS_NAME}",
                WAIT_TIMEOUT,
                "${SOURCE_REPO_WORKING_DIRECTORY}",
                "TOB_THEME=${TOB_THEME_NAME}")
        }
      }
    }
  }

  stage("Build ${RUNTIME_NAME}") {
    script {
      openshift.withCluster() {
        openshift.withProject() {
          echo "Building the ${RUNTIME_NAME} image ..."
          build("${RUNTIME_NAME}", WAIT_TIMEOUT)
        }
      }
    }
  }

  stage("Build ${APP_NAME}") {
    script {
      openshift.withCluster() {
        openshift.withProject() {

          echo "Building the ${APP_NAME} image ..."
          build("${APP_NAME}", WAIT_TIMEOUT)
        }
      }
    }
  }

  stage("Deploy ${DEPLOYMENT_ENVIRONMENT_TAGS[0]}") {
    script {
      openshift.withCluster() {
        openshift.withProject() {

          echo "Tagging ${APP_NAME} for deployment to ${DEPLOYMENT_ENVIRONMENT_TAGS[0]} ..."

          // Don't tag with BUILD_ID so the pruner can do it's job; it won't delete tagged images.
          // Tag the images for deployment based on the image's hash
          def IMAGE_HASH = getImageTagHash("${APP_NAME}")
          echo "IMAGE_HASH: ${IMAGE_HASH}"
          openshift.tag("${APP_NAME}@${IMAGE_HASH}", "${APP_NAME}:${DEPLOYMENT_ENVIRONMENT_TAGS[0]}")
        }

        echo "Watching rollout of ${APP_NAME}${SUFFIX} in ${NAME_SPACE}-${DEPLOYMENT_ENVIRONMENT_TAGS[0]} ..."
        openshift.withProject("${NAME_SPACE}-${DEPLOYMENT_ENVIRONMENT_TAGS[0]}") {
            def dc = openshift.selector('dc', "${APP_NAME}${SUFFIX}")
            // Wait for the deployment to complete.
            // This will wait until the desired replicas are all available
            dc.rollout().status()
        }

        echo "Deployment Complete."
      }
    }
  }
}

/** These are utility functions and variables that can ONLY be used inside the pipeline,
  * they will NOT be available when loading this file inside another pipeline.
  **/

def APP_NAME = 'frontend'
def BUILD_CONFIG = "${APP_NAME}-artifacts-binary"

// // Edit your environment TAG names below
// def TAG_NAMES = ['dev', 'test', 'prod']

// //EDIT LINE BELOW (Change `IMAGESTREAM_NAME` so it matches the name of your *output*/deployable image stream.)
// def IMAGESTREAM_NAME = 'angular-on-nginx'

// // you'll need to change this to point to your application component's folder within your repository
// def CONTEXT_DIRECTORY = 'tob-web'

// // EDIT LINE BELOW
// // Add a reference to the RUNTIME_BUILD_CONFIG, if you are using a runtime that needs to be built.
// // Otherwise comment out the line and the associated build script.
// def RUNTIME_BUILD_CONFIG = 'nginx-runtime'

// // EDIT LINE BELOW (Add a reference to the CHAINED_BUILD_CONFIG)
// def CHAINED_BUILD_CONFIG = 'angular-on-nginx-build'

// // The name of your deployment configuration; used to verify the deployment
// def DEPLOYMENT_CONFIG_NAME = 'angular-on-nginx'

// // The namespace of you dev deployment environment.
// def DEV_NAME_SPACE = 'devex-von-dev'

@NonCPS
boolean triggerBuild(String contextDirectory) {
  // Determine if code has changed within the source context directory.
  def changeLogSets = currentBuild.changeSets
  def filesChangeCnt = 0
  for (int i = 0; i < changeLogSets.size(); i++) {
    def entries = changeLogSets[i].items
    for (int j = 0; j < entries.length; j++) {
      def entry = entries[j]
      def files = new ArrayList(entry.affectedFiles)
      for (int k = 0; k < files.size(); k++) {
        def file = files[k]
        def filePath = file.path
        if (filePath.contains(contextDirectory)) {
          filesChangeCnt = 1
          k = files.size()
          j = entries.length
        }
      }
    }
  }

  if ( filesChangeCnt < 1 ) {
    echo('The changes do not require a build.')
    return false
  }
  else {
    echo('The changes require a build.')
    return true
  }
}

// Get an image's hash tag
String getImageTagHash(String imageName, String tag = "") {

  if(!tag?.trim()) {
    tag = "latest"
  }

  def istag = openshift.raw("get istag ${imageName}:${tag} -o template --template='{{.image.dockerImageReference}}'")
  return istag.out.tokenize('@')[1].trim()
}
