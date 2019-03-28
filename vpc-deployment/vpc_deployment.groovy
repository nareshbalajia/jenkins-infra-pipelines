tf_git_url = "git@github.com:nareshbalajia/awsvpc-tf-module.git"
tf_git_branch = "master"
job_name = "deploy-vpc-infra"
job_path = "${new File(__FILE__).parent}"
jenkinsfile = job_path + "/Jenkinsfile"
REGION = "ap-southeast-1"

pipelineJob(job_name) {
  description("Jenkins pipeline to deploy VPC infrastructure")
  logRotator(-1,100)
  parameters {
    stringParam("TF_GIT_URL", tf_git_url, "GIT URL of repo containing Terraform template for infrastructure")
    stringParam("TF_GIT_BRANCH", tf_git_branch, "GIT Branch of repo containing cf stack for infrastructure")
    stringParam("TF_STATE_BUCKET", "jenkins-terraform-s3-bucket", "The S3 bucket name where TF state is stored")
    stringParam("REGION", REGION, "Target region where this stack needs to be deployed")
    choiceParam("OPERATION", ["create/update", "destroy"], "Terraform operation to perform")
  }
  definition {
    cps {
      sandbox()
      script(readFileFromWorkspace(jenkinsfile))
    }
  }
}
