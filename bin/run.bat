javac -cp amqp-client-5.7.1.jar BolsaDeValores.java;
java -cp .;amqp-client-5.7.1.jar;slf4j-api-1.7.26.jar;slf4j-simple-1.7.26.jar;%~p0\bin BolsaDeValores;
javac -cp amqp-client-5.7.1.jar Corretora.java Planilha.java Aplicacao.java;
java -cp .;amqp-client-5.7.1.jar;slf4j-api-1.7.26.jar;slf4j-simple-1.7.26.jar;%~p0\bin Aplicacao;