# Resize Photo

B2W Challenge - Resized Photo

## Inicial

Instruções para build do projeto

### Pré requisitos

* Java 8
* Maven

### Build

```
git clone https://github.com/jsantos88/resize-photo.git
cd resize-photo
mvn clean package
```
### Running Application

```
java -jar target/resize-photo.jar
```

Obs.: Para o funcionamento da aplicação
1) Execute a api http://localhost:8080/resizephoto (isso lê o json inicial e gera as imagens)
2) Para obter as urls com as imagens redimensionadas: http://localhost:8080/resizephoto/urls
3) Basta clicar no link desejado para visualizar as imagens redimensionadas
