terraform {
  backend "s3" { # onde?
    bucket = "terraform-state-rospiel" #o nome dado na criação do bucket aws
    key = "study-course-aws" #nome de registro
    region = "us-east-1"
    profile = "terraform" #qual profile usar?
  }
}