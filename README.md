# TrabFinal_ProgMobile

Este trabalho foi desenvolvido por:
* Isadora Sandri da Silva | 2019.1905.009-4
  
## Visão Geral do Software
Aplicativo para rastreio de encomendas dos Correios com a possibilidade de criação de conta para armazenamento de códigos de rastreio e histórico de encomendas. Além da possibilidade de rastreio sem autenticação, porém sem acesso a histórico ou códigos salvos.

## Requisitos Funcionais
* Usuário Padrão: Pode rastrear encomendas a partir do código, pode salvar o código para ter fácil acesso a ele em outra oportunidade e pode gerar um histórico de encomendas salvas.
  * Necessário e-mail e senha para cadastro do usuário.
  * Usuário com nome completo, CPF, data de nascimento e endereço.
  * Pode ou não utilizar uma foto de perfil.
* Usuário Não Autenticado: Pode rastrear encomendas a partir do código.
* Processamento: Ao receber um código, é feito uma requisição GET ao site dos Correios e capturado a imagem CAPTCHA que é exibida ao usuário para que seja decifrada. Uma vez que o usuário inseriu a sequência de caracteres correspondentes ao CAPTCHA, é feita uma nova requisição GET solicitando o rastreamento.
* Tratamento de Dados: É recebido o JSON de resposta pela requisição, tratado para capturar os dados do rastreio e exibido ao usuário.
