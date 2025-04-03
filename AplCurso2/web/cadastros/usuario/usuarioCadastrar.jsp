<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@page contentType="text/html" pageEncoding="iso-8859-1"%>
<jsp:include page="/header.jsp"/>
<jsp:include page="/menu.jsp"/>

<div class="container-fluid">
    <!-- Page Heading -->
    <h1 class="h3 mb-2 text-gray-800">Usuários</h1>
    <p class="mb-4">Formulário de Cadastro</p>

    <a class="btn btn-secondary mb-4" href="${pageContext.request.contextPath}/UsuarioListar">
        <i class="fas fa-undo-alt"></i>
        <strong>Voltar</strong>
    </a>
    <div class="row">
        <!-- Campos de cadastramento -->        
        <div class="col-lg-9">
            <div class="card shadow mb-4">
                <div class="card-body">
                    <div class="form-group">
                        <label>Id</label>
                        <input class="form-control" type="text" name="id" id="id" 
                               value="${usuario.id}" readonly="readonly"/>
                    </div>
                    <div class="form-group">
                        <label>Nome</label>
                        <input class="form-control" type="text" name="nome" id="nome" 
                               value="${usuario.nome}" size="100" maxlength="100"/>
                    </div>
                    <div class="form-group">
                        <label>CPF</label>
                        <input class="form-control" type="text" name="cpf" id="cpf" 
                               value="${usuario.cpf}" size="11" maxlength="11"/>
                    </div>
                    <div class="form-group">
                        <div class="form-line row">
                            <div class="col-sm">
                                <label>Data de Nascimento</label>
                                <input class="form-control" type="date" name="datanascimento" id="datanascimento" 
                                       value="${usuario.dataNascimento}"/>
                            </div>
                            <div class="col-sm">
                                <label>Valor do Salário</label>
                                <input class="form-control" type="text" style="text-align:right;" 
                                           name="salario" id="salario" 
                                           value="<fmt:formatNumber value='${usuario.salario}' type='currency'/>" />
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <label>Email</label>
                        <input class="form-control" type="text" name="email" id="email" 
                               value="${usuario.email}" size="100" maxlength="100"/>
                    </div>
                    <div class="form-group">
                        <label>Senha</label>
                        <input class="form-control" type="text" name="senha" id="senha" 
                               value="${usuario.senha}" size="100" maxlength="100"/>
                    </div>
                    <!-- Botão de Confirmação --> 
                    <div class="form-group">
                        <button class="btn btn-success" type="submit" id="submit" onclick="validarCampos()">
                            Salvar Documento</button>
                    </div> 
                </div>
            </div>
        </div>
    </div>
</div>

<style type="text/css">
    .inputfile {
        /* visibility: hidden etc. wont work */
        width: 0.1px;
        height: 0.1px;
        opacity: 0;
        overflow: hidden;
        position: absolute;
        z-index: -1;    }
    .inputfile:focus + label {
        /* keyboard navigation */
        outline: 1px dotted #000;
        outline: -webkit-focus-ring-color auto 5px;    }
    .inputfile + label * {
        pointer-events: none;    }
    .borda{
        position: relative;
        margin: 0 20px 30px 0;
        padding: 10px;
        border: 1px solid #e1e1e1;
        border-radius: 3px;
        background: #fff;
        -webkit-box-shadow: 0px 0px 3px rgba(0,0,0,0.06);
        -moz-box-shadow: 0px 0px 3px rgba(0,0,0,0.06);
        box-shadow: 0px 0px 3px rgba(0,0,0,0.06);    }
</style>

<script>
    $(document).ready(function () {
        console.log("entrei na ready do documento");
        $("#salario").maskMoney({
            prefix: 'R$',
            suffix: '',
            allowZero: false,
            allowNegative: true,
            allowEmpty: false,
            doubleClickSelection: true,
            selectAllOnFocus: true,
            thousands: '.',
            decimal: ",",
            precision: 2,
            affixesStay: true,
            bringCareAtEndOnFocus: true
        });
        
        console.log('Ativo evento focus campo cpf');
        $('#cpf').focus(function(){
            trocaMascara();
            this.select();
        });
        
        $('#cpf').blur(function(){
            var cpfLimpo = $('#cpf').unmask().val();
            console.log("CPF Limpo:");
            console.log(cpfLimpo);
            if (!validarCpfCnpj(cpfLimpo)){
                 Swal.fire({
                     position: 'center',
                     icon: 'error',
                     title: 'Verifique o CPF/CNPJ!',
                     showConfirmButton: true,
                     timer: 10000
                 });
             }else{
                trocaMascara($('#cpf').val());
            }
        });
    });
    
    function trocaMascara(cpfCnpj) {
        console.log("entrei no troca mascara");
        console.log(cpfCnpj);
        
        // Se o parâmetro for undefined (ou não passado), pega do campo
        if (typeof cpfCnpj === 'undefined') {
            cpfCnpj = "";
        }

        cpfCnpj = cpfCnpj.trim();

        if (cpfCnpj !== "") {
            var masks = ['999.999.999-99', '99.999.999/9999-99'];
            var cpfcnpj = cpfCnpj.replace(/\D/g, ''); // remove tudo que não for número
            var mask = (cpfcnpj.length > 11) ? masks[1] : masks[0];
            console.log("trocou: " + mask);
            $('#cpf').unmask().mask(mask);
        } else {
            console.log("limpou mascara");
            $('#cpf').unmask();
        }
    }

    function validarCampos() {
        console.log("entrei na validação de campos");
        if (document.getElementById("nome").value === '') {
            Swal.fire({
                position: 'center',
                icon: 'error',
                title: 'Verifique o nome do usuário!',
                showConfirmButton: false,
                timer: 1000
            });
            $("#nome").focus();
        } else if (document.getElementById("cpf").value === '') {
            Swal.fire({
                position: 'center',
                icon: 'error',
                title: 'Verifique a Data do nascimento!',
                showConfirmButton: false,
                timer: 1000
            });
            $("#cpf").focus();
        } else if (document.getElementById("email").value === '') {
            Swal.fire({
                position: 'center',
                icon: 'error',
                title: 'Verifique a Data do nascimento!',
                showConfirmButton: false,
                timer: 1000
            });
            $("#email").focus();
        } else if (document.getElementById("senha").value === '') {
            Swal.fire({
                position: 'center',
                icon: 'error',
                title: 'Verifique a Data do nascimento!',
                showConfirmButton: false,
                timer: 1000
            });
            $("#senha").focus();
        } else if (document.getElementById("datanascimento").value === '') {
            Swal.fire({
                position: 'center',
                icon: 'error',
                title: 'Verifique a Data do nascimento!',
                showConfirmButton: false,
                timer: 1000
            });
            $("#datanascimento").focus();
        } else if (document.getElementById("salario").value === '') {
            Swal.fire({
                position: 'center',
                icon: 'error',
                title: 'Verifique o valor do salário!',
                showConfirmButton: false,
                timer: 1000
            });
            $("#salario").focus();
        } else {
            gravarDados();
        }
    }
    
    function gravarDados() {
        console.log("Gravando dados ....");
        $.ajax({
            type: 'post',
            url: 'DespesaCadastrar',
            data: {
                id: $('#id').val(),
                nome: $('#nome').val(),
                cpf: $('#cpf').unmask().val(),
                datanascimento: $('#datanascimento').val(),
                salario: $('#salario').val(),
                email: $('#email').val(),
                senha: $('#senha').val()
            },
            success:
                    function (data) {
                        console.log("reposta servlet->");
                        console.log(data);
                        if (data == 1) {
                            Swal.fire({
                                position: 'center',
                                icon: 'success',
                                title: 'Sucesso',
                                text: 'Usuario gravado com sucesso!',
                                showConfirmButton: true,
                                timer: 10000
                            }).then(function(){
                                window.location.href = "${pageContext.request.contextPath}/UsuarioListar";
                            })
                        } else {
                            Swal.fire({
                                position: 'center',
                                icon: 'error',
                                title: 'Erro',
                                text: 'Não foi possível gravar o usuário!',
                                showConfirmButton: true,
                                timer: 10000
                            }).then(function(){
                                window.location.href = "${pageContext.request.contextPath}/UsuarioListar";
                            })
                        }
                    },
            error:
                    function (data) {
                        window.location.href = "${pageContext.request.contextPath}/UsuarioListar";
                    }
        });
    }
    
</script>   
<jsp:include page="/footer.jsp"/>