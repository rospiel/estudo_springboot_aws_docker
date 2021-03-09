#!/bin/bash

echo "Starting ansible..."
# ANSIBLE_HOST_KEY_CHECKING --> desligando a pergunta se confiamos na chave de acesso
# -i ../terraform/hosts --> dizendo onde encontrar os hosts das máquinas
# --private-key ../terraform/key/studyaws_key --> dizendo onde encontrar a chave de acesso das máquina
# studyaws-playbook.yml  --> o arquivo com os comandos
# executar tudo em uma única linha
ANSIBLE_HOST_KEY_CHECKING=false ansible-playbook -i ../terraform/hosts --private-key ../terraform/key/studyaws_key studyaws-playbook.yml -v

