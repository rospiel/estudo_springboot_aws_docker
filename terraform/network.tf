resource "aws_vpc" "main" {
  cidr_block = "192.168.0.0/16"

  # útil quando desejamos filtrar ou validar custos
  tags = {
    Name = "studyaws"
  }
}

resource "aws_subnet" "private_subnet" {
  # contador onde explicitamente dizemos que vamos ter 3 subnets
  count = 3

  # exato dados do 1 resource sendo 1 string + . + 2 string + . + id[variável de retorno do resource]
  vpc_id = "${aws_vpc.main.id}"

  # cidrsubnet se trata de uma função existente na aws pra dinâmismo na criação das subnets
  cidr_block = "${cidrsubnet(aws_vpc.main.cidr_block, 8, count.index + 10)}"

  # availability_zones se trata de uma variável criada nessa mesma pasta de nome variables 
  availability_zone = "${var.availability_zones[count.index]}"

  tags = {
    Name = "studyaws_private_subnet_${count.index}"
  }
}

resource "aws_subnet" "public_subnet" {
  # contador onde explicitamente dizemos que vamos ter 3 subnets
  count = 3

  # exato dados do 1 resource sendo 1 string + . + 2 string + . + id[variável de retorno do resource]
  vpc_id = "${aws_vpc.main.id}"

  # cidrsubnet se trata de uma função existente na aws pra dinâmismo na criação das subnets
  cidr_block = "${cidrsubnet(aws_vpc.main.cidr_block, 8, count.index + 20)}"

  # availability_zones se trata de uma variável criada nessa mesma pasta de nome variables
  availability_zone = "${var.availability_zones[count.index]}"

  # disponibiliza um ip publico
  map_public_ip_on_launch = true

  tags = {
    Name = "studyaws_public_subnet_${count.index}"
  }
}

resource "aws_internet_gateway" "studyaws_gateway" {
  vpc_id = "${aws_vpc.main.id}"
}

resource "aws_route_table" "route_studyaws_gateway" {
  vpc_id = "${aws_vpc.main.id}"

  route {
    cidr_block = "0.0.0.0/0"
    gateway_id = "${aws_internet_gateway.studyaws_gateway.id}"
  }
}

resource "aws_route_table_association" "route_table_association" {
  count = 3

  route_table_id = "${aws_route_table.route_studyaws_gateway.id}"
  subnet_id = "${element(aws_subnet.public_subnet.*.id, count.index)}"
}

