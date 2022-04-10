# Pra que serve

- evitar que dois usuarios consigam alterar simultaneamente a mesma informacao
- o primeiro usuario consegue alterar
- o segundo usuario é informado que aquela opcao ja foi alterada antes
- utiliza um sistema de versionamento

# Como testar

- abrir um console no eclipse
- selecionar produto, ir em editar e alterar o nome SEM APERTAR ENTER
- abrir outro console no eclipse
- selecionar o mesmo produto, alterar o nome e confirmar
- voltar no console original e tentar alterar o nome
