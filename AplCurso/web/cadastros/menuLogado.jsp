<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<h1>Módulo Cadastros</h1>
<h2>Menu Principal - Logado: ${sessionScope.nomeusuario} - ${sessionScope.tipousuario} - 
    <a href="${pageContext.request.contextPath}/UsuarioDeslogar">Sair do Sistema</a></h2>
<c:if test="${sessionScope.tipousuario == 'Administrador'}">
    <jsp:include page="menuAdministrador.jsp"/>
</c:if>
<c:if test="${sessionScope.tipousuario == 'Cliente'}">
    <jsp:include page="menuCliente.jsp"/>
</c:if>
<c:if test="${sessionScope.tipousuario == 'Fornecedor'}">
    <jsp:include page="menuFornecedor.jsp"/>
</c:if>




