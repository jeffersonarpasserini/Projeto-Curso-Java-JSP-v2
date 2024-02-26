<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="iso-8859-1"%>
<jsp:include page="/header.jsp"/>
<jsp:include page="/cadastros/menuLogado.jsp"/>

<form name="cadastrarcidade" action="CidadeCadastrar" method="POST">
    <table align="center" border="0">
        <thead>
            <tr>
                <th colspan="2" align="center">
                    Cadastro de Cidade</th>
            </tr>
            <tr>
                <th colspan="2" align="center">${mensagem}</th>
            </tr>
        </thead>
        <tbody>
            <tr><td>ID: </td>
            <td><input type="text" name="idcidade" id="idcidade" value="${cidade.idCidade}" readonly="readonly" /></td></tr>
            <tr><td>Nome: </td>
            <td><input type="text" name="nomecidade" id="nomecidade" value="${cidade.nomeCidade}" 
                       size="50" maxlength="50" /></td></tr>
            <tr>
                <td>Estado: </td>
                <td>
                <select name="idestado" id="idestado">
                    <option value="">Selecione</option>
                    <c:forEach var="estado" items="${estados}">
                        <option value="${estado.idEstado}" 
                            ${cidade.estado.idEstado == estado.idEstado ? "selected" : ""}>
                            ${estado.nomeEstado}
                        </option>
                    </c:forEach>
                </select>
                </td>
            </tr>
            <tr><td>
                    <input type="hidden" name="situacao" id="situacao" value="${cidade.situacao}" readonly="readonly" />
                </td></tr>
            <tr><td colspan="2" align="center">
                    <input type="submit" name="cadastrar" id="cadastrar" value="Cadastrar" />
                    <input type="reset" name="limpar" id="limpar" value="Limpar" />
                </td>
            </tr>
            <tr>
                <td align="center" colspan="2"><h5><a href="index.jsp">Voltar à Página Inicial</a></h5></td>
            </tr>
        </tbody>
    </table>
</form>  
<%@ include file="/footer.jsp" %>

