<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="pt-br">
<head>
    <%@include file="base-head.jsp" %>
</head>
<body>
<%@include file="nav-menu.jsp" %>

<div id="container" class="container-fluid">
    <h3 class="page-header">Adicionar Produto</h3>

    <form action="${pageContext.request.contextPath}/product/${action}" method="POST">
        <input type="hidden" value="${product.getId()}" name="productId">
        <div class="row">
            <div class="form-group col-md-4">
                <label for="name">Nome</label>
                <input type="text" class="form-control" id="name" name="name"
                       autofocus="autofocus" placeholder="Nome do produto"
                       required oninvalid="this.setCustomValidity('Por favor, informe o nome do produto.')"
                       oninput="setCustomValidity('')"
                       value="${product.getName()}">
            </div>

            <div class="form-group col-md-4">
                <label for="description">Descrição</label>
                <input type="text" class="form-control" id="description" name="description"
                       placeholder="Descrição do produto"
                       required oninvalid="this.setCustomValidity('Por favor, informe a descrição do produto.')"
                       oninput="setCustomValidity('')"
                       value="${product.getDescription()}">
            </div>

            <div class="form-group col-md-4">
                <label for="price">Preço</label>
                <input type="number" class="form-control" id="price" name="price"
                       placeholder="Preço do produto" step="0.01"
                       required oninvalid="this.setCustomValidity('Por favor, informe o preço do produto.')"
                       oninput="setCustomValidity('')"
                       value="${product.getPrice()}">
            </div>
        </div>
        <hr/>
        <div id="actions" class="row pull-right">
            <div class="col-md-12">
                <a href="${pageContext.request.contextPath}/products" class="btn btn-default">Cancelar</a>
                <button type="submit" class="btn btn-primary">${not empty product ? "Alterar Produto" : "Cadastrar Produto"}</button>
            </div>
        </div>
    </form>
</div>

<script src="js/jquery.min.js"></script>
<script src="js/bootstrap.min.js"></script>
</body>
</html>
