provider "aws" {
  version = "~> 3.27"  #Versão encontrada no git
  shared_credentials_file = "~/.aws/credentials" #Onde se encontra as credenciais
  profile = "terraform" #Nome do profile criado pro terraform no aws cli
}