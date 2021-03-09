resource "aws_lb" "alb" {
  name = "studyaws-alb"
  security_groups = ["${aws_security_group.allow_load_balancer.id}"]
  subnets = flatten(chunklist(aws_subnet.public_subnet.*.id, 1))

  # em produção é interessante habilitar pra impedir que seja deletado por engano
  enable_deletion_protection = false

}

# Criando o group
resource "aws_lb_target_group" "alb_tg" {
  name = "studyaws"
  # porta da aplicação
  port = 8080
  protocol = "HTTP"
  vpc_id = "${aws_vpc.main.id}"

  health_check {
    # onde validar a saúde do recurso
    path = "/actuator/health"
    # se saudável é 200
    matcher = 200
    # no intervalo de 2min
    interval = 120
    # só saudável se 2 chamadas com 200
    healthy_threshold = 2
    # sem saúde se 2 chamadas <> 200
    unhealthy_threshold = 2
    # aguarde por resposta
    timeout = 10
  }
}

# Onde fazemos associação entre os target groups e as instâncias
resource "aws_lb_target_group_attachment" "alb_group_attachment" {
  # qtde de instâncias que temos
  count = "3"

  target_group_arn = "${aws_lb_target_group.alb_tg.arn}"
  target_id = "${element(aws_instance.instances.*.id, count.index)}"
  port = 8080
}

# Associação do group com o load balancer
resource "aws_lb_listener" "alb_listener" {
  default_action {
    type = "forward"
    target_group_arn = "${aws_lb_target_group.alb_tg.arn}"
  }

  load_balancer_arn = "${aws_lb.alb.arn}"
  port = 80
  protocol = "HTTP"
}

# Retornando o endereço de acesso ao recurso
output "loadBalancer" {
  value = "${aws_lb.alb.dns_name}"
}