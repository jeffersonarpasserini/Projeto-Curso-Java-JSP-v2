<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@page contentType="text/html" pageEncoding="iso-8859-1"%>
<jsp:include page="/header.jsp"/>
<jsp:include page="/menu.jsp"/>

<div class="container-fluid">
    <!-- Page Heading -->
    <h1 class="h3 mb-2 text-gray-800">Usuários</h1>
    <p class="mb-4">Cadastro de Usuários</p>
    
    <a class="btn btn-success mb-4" href="${pageContext.request.contextPath}/UsuarioNovo">
        <i class="fas fa-sticky-note"></i>
        <strong>Novo</strong>
    </a>
    
    <div class="card shadow">
        <div class="card-body">
            <table id="datatable" class="display">
                <thead>
                    <tr>
                        <th align="right">Id</th>
                        <th align="left">Nome</th>
                        <th align="left">CPF</th>
                        <th align="right">Email</th>
                        <th align="center">Nascimento</th>
                        <th align="right">Valor Salario</th>
                        <th Align="center">Excluir</th>
                        <th Align="center">Alterar</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="usuario" items="${usuarios}"> 
                        <tr>
                            <td align="right">${usuario.id}</td>
                            <td align="left">${usuario.nome}</td>
                            <td align="left">${usuario.cpf}</td>
                            <td align="left">${usuario.email}</td>
                            <td align="center"><fmt:formatDate pattern = "dd/MM/yyyy" value = "${usuario.dataNascimento}" />
                            </td>
                            <td align="right"><fmt:formatNumber value = "${usuario.salario}" type = "currency"/></td>
                            <td align="center">
                                <a href="${pageContext.request.contextPath}/UsuarioExcluir?id=${usuario.id}">
                                    <button>Excluir</button>
                                </a>
                            </td>                        
                            <td align="center">
                                <a href="${pageContext.request.contextPath}/UsuarioCarregar?id=${usuario.id}">
                                   <button>Alterar</button>
                                </a>
                            </td>             
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>      
<script>
    $(document).ready(function() {
            console.log('entrei ready');
            //Carregamos a datatable
            //$("#datatable").DataTable({});
            $('#datatable').DataTable({
                "oLanguage": {
                    "sProcessing": "Processando...",
                    "sLengthMenu": "Mostrar _MENU_ registros",
                    "sZeroRecords": "Nenhum registro encontrado.",
                    "sInfo": "Mostrando de _START_ até _END_ de _TOTAL_ registros",
                    "sInfoEmpty": "Mostrando de 0 até 0 de 0 registros",
                    "sInfoFiltered": "",
                    "sInfoPostFix": "",
                    "sSearch": "Buscar:",
                    "sUrl": "",
                    "oPaginate": {
                        "sFirst": "Primeiro",
                        "sPrevious": "Anterior",
                        "sNext": "Seguinte",
                        "sLast": "Último"
                    }
                }
            });
        });
</script>
 <%@include file="/footer.jsp"%>
