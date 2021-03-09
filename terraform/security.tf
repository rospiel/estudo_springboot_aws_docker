resource "aws_security_group" "allow_ssh" {
  vpc_id = "${aws_vpc.main.id}"
  name = "studyaws_allow_ssh"

  # regras de entrada
  ingress {
    from_port = 22
    protocol = "tcp"
    to_port = 22
    # ip's que vamos permitir acessar, neste caso o ip público de nossa máquina de desenvolvimento
    cidr_blocks = ["${var.my_public_ip}"]
  }
}

resource "aws_security_group" "database" {
  vpc_id = "${aws_vpc.main.id}"
  name = "studyaws_database"

  ingress {
    from_port = 5432
    protocol = "tcp"
    to_port = 5432
    self = true
  }
}

# Liberando a saída pro ansible playbook
resource "aws_security_group" "allow_outbound" {
  vpc_id = "${aws_vpc.main.id}"
  name = "studyaws_allow_outbound"

  egress {
    # qualquer
    from_port = 0
    # qualquer
    protocol = "-1"
    # qualquer
    to_port = 0
    cidr_blocks = ["0.0.0.0/0"]
  }
}

# Liberando a comunicação entre os managers do docker swarm
resource "aws_security_group" "cluster_communication" {
  vpc_id = "${aws_vpc.main.id}"
  name = "studyaws_cluster_communication"

  #As portas em questão são obtidas por meio da documentação
  ingress {
    from_port = 2377
    protocol = "tcp"
    to_port = 2377
    self = true
  }

  ingress {
    from_port = 7946
    protocol = "tcp"
    to_port = 7946
    self = true
  }

  ingress {
    from_port = 7946
    protocol = "udp"
    to_port = 7946
    self = true
  }

  ingress {
    from_port = 4789
    protocol = "udp"
    to_port = 4789
    self = true
  }
}

# Configuração de segurança pra acessar o portainer
resource "aws_security_group" "allow_app" {
  vpc_id = "${aws_vpc.main.id}"
  name = "studyaws_allow_app"

  # portainer
  ingress {
    from_port = 9000
    protocol = "tcp"
    to_port = 9000
    cidr_blocks = ["${var.my_public_ip}"]
  }

  # app
  ingress {
    from_port = 8080
    protocol = "tcp"
    to_port = 8080
    cidr_blocks = flatten(chunklist(aws_subnet.public_subnet.*.cidr_block, 1))
  }
}

# Configuração de segurança do load balancer
resource "aws_security_group" "allow_load_balancer" {
  vpc_id = "${aws_vpc.main.id}"
  name = "studyaws_allow_loadbalancer"

  ingress {
    from_port = 80
    protocol = "tcp"
    to_port = 80
    cidr_blocks = ["0.0.0.0/0"]
  }

  egress {
    from_port = 8080
    protocol = "tcp"
    to_port = 8080
    cidr_blocks = flatten(chunklist(aws_subnet.public_subnet.*.cidr_block, 1))
  }
}
