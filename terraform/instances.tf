# Informando onde encontrar a chave de acesso pública
# da instâncias
resource "aws_key_pair" "keypair" {

  # usamos a função key pra obtençao da chave informando o local
  public_key = "${file("key/studyaws_key.pub")}"
}

resource "aws_instance" "instances" {
  count = 3

  # sistema operacional
  ami = "ami-0915bcb5fa77e4892"

  # tipo do sistema operacional
  instance_type = "t2.micro"

  # aqui estamos usando uma função da aws de nome element, como temos
  # 3 subnets públicas vamos criar 3 instâncias e atrelar as mesmas
  # as respectivas subnets por meio de seus id's
  subnet_id = "${element(aws_subnet.public_subnet.*.id, count.index)}"

  # uma vez que a chave de acesso pública foi criada e disponibiliza
  # dizemos onde achar
  key_name = "${aws_key_pair.keypair.key_name}"

  # qual regra de segurança vamos utilizar --> No máximo 5
  vpc_security_group_ids = ["${aws_security_group.allow_ssh.id}",
    "${aws_security_group.allow_outbound.id}",
  "${aws_security_group.cluster_communication.id}",
  "${aws_security_group.allow_app.id}",
  "${aws_security_group.database.id}"]

  tags = {
    Name = "studyaws_instances"
  }
}

# Cadastrando os ip's fornecidos pela aws no arquivo
data "template_file" "hosts" {
  # pegue o arquivo
  template = "${file("./template/hosts.tpl")}"

  # preencha as variáveis encontradas no arquivo com os ip's públicos
  vars = {
    PUBLIC_IP_0 = "${aws_instance.instances.*.public_ip[0]}"
    PUBLIC_IP_1 = "${aws_instance.instances.*.public_ip[1]}"
    PUBLIC_IP_2 = "${aws_instance.instances.*.public_ip[2]}"

    PRIVATE_IP_0 = "${aws_instance.instances.*.private_ip[0]}"
  }
}

# Gerando arquivo com os ip's públicos fornecidos pela aws
resource "local_file" "hosts" {
  # pegue o conteúdo do arquivo, rendered --> afirmamos que aguarde qualquer processo sendo executado por sobre o arquivo
  content = "${data.template_file.hosts.rendered}"
  # gere um novo arquivo com o conteúdo
  filename = "./hosts"
}

# útil pra nos printar os ips ao final do terraform apply
output "public_ips" {
  value = "${join(", ", aws_instance.instances.*.public_ip)}"
}