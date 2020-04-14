# comunicacao-indireta

Trabalho feito para a disciplina de Desenvolvimento de Aplicações Móveis e Distribuídas com o objetivo de simular troca de mensagens em uma bolsa de valores.
O trabalho foi desenvolvido com o auxílio do middleware RabbitMQ e da biblioteca amqp.
Para executar os arquivos, é necessário ter Java instalado na máquina (e possivelmente configurar seu `%CLASSPATH%` no Windows).

Execução:

Windows:

Para execução simplificada do programa (ouvindo todos os tópicos), há dois arquivos .bat dentro da pasta src.
Basta executar o arquivo bolsavalores.bat e depois executar aplicacao.bat.
Caso queira mandar mais de uma mensagem, deixe o bolsavalores.bat em execução e execute aplicacao.bat para
quantas mensagens desejar enviar.

Para mudar o tópico, basta abrir o arquivo bolsavalores.bat num editor de texto, editar o valor do parâmetro e executar os arquivos 
novamente.

Linux:

Abra um terminal;
Execute os comandos:
javac  -cp amqp-client-5.7.1.jar BolsaDeValores.java
java  -cp .:amqp-client-5.7.1.jar:slf4j-api-1.7.26.jar:slf4j-simple-1.7.26.jar:DIRETÓRIO_DO_PROJETO\src BolsaDeValores "compra_ou_venda.ativo"

Abra outro terminal;
Execute os comandos:
javac  -cp amqp-client-5.7.1.jar Corretora.java Planilha.java Aplicacao.java
java  -cp .:amqp-client-5.7.1.jar:slf4j-api-1.7.26.jar:slf4j-simple-1.7.26.jar:DIRETÓRIO_DO_PROJETO\src Aplicacao
