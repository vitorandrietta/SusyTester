# Susy Tester

Esse programa tem por objetivo facilitar a verificação dos casos de teste abertos dos laboratorios cadastrados no [Susy] (http://www.ic.unicamp.br/~susy/), através de uma interface para gerenciar os testes.

O programa utiliza Diff no Linux e FC (não pega a diferença de \n na ultima linha) no Windows, para entender as diferenças geradas:
[Entendendo Comando Diff no linux] (http://www.computerhope.com/unix/udiff.htm)

Os arquivos são baixados na pasta "TestesSusyApp" (durante a execução), onde o programa esta sendo executado

NÃO SE DEVE ficar trocando rapidamente o valor das opções e clicando indiscriminadamente no botão testar, para evitar requisições desnecessárias ao Susy

Qualquer dúvida, sugestão, ou aviso de erro: vitorandrietta@gmail.com

##Download (A aplicação tem melhor desempenho em ambiente linux)

- [Susy Tester em JAR] (https://drive.google.com/file/d/0B5QULOuZcf65ODBtZEppSzMyV0U/view?usp=sharing) (Adicionar Permissão de execução para linux)
- [Susy Tester em .exe] (https://drive.google.com/file/d/0B5QULOuZcf65bU5YQ1ExOUszVDA/view?usp=sharing)

##Guia de utilização

Tela Inicial, o primeiro campo ja carrega automaticamente todas as turmas registradas acessando à pagina do Susy

<p align="center">
   <img src="https://github.com/vitorandrietta/SusyTester/blob/master/SusyTesterImages/initial.PNG">
</p>

Após escolher uma turma, todos os laboratórios daquela turma serão carregados por consulta ao site no campo de Laboratório.

<p align="center">
   <img src="https://github.com/vitorandrietta/SusyTester/blob/master/SusyTesterImages/lab.PNG">
</p>

Então Só resta escolher o compilado do programa para efetuar o teste, clicando no campo "Executável" e selecionando o programa

<p align="center">
   <img src="https://github.com/vitorandrietta/SusyTester/blob/master/SusyTesterImages/selector.PNG">
</p>


Agora só resta clicar No botao "Testar" para efetuar o teste


<p align="center">
   <img src="https://github.com/vitorandrietta/SusyTester/blob/master/SusyTesterImages/ready.PNG">
</p>

Espere um tempo até aque a tela de Status seja exibida (o tempo varia de acordo com o numero de testes)

<p align="center">
   <img src="https://github.com/vitorandrietta/SusyTester/blob/master/SusyTesterImages/differenceStatus.PNG">
</p>


Nese caso nenhum teste passou, Azar :/ . quando algum teste nao passa, é só clicar em seu respectivo botão para visualizar as diferenças. Diferenças no Windows do primeiro teste (FC) :

<p align="center">
   <img src="https://github.com/vitorandrietta/SusyTester/blob/master/SusyTesterImages/resultWindows.PNG">
</p>


Diferenças geradas no Linux no primeiro teste (DIF):


<p align="center">
   <img src="https://github.com/vitorandrietta/SusyTester/blob/master/SusyTesterImages/linux.png">
</p>

<center>Quando os testes passam, nao há botão gerado, apenas a informação que o teste passou</center>

<p align="center">
   <img src="https://github.com/vitorandrietta/SusyTester/blob/master/SusyTesterImages/OK.PNG">
</p>
