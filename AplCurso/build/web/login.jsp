<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="header.jsp"/>

<h1>Sistema Exemplo - CRUD - Deslogado</h1>
        <h2>Login Sistema</h2>
        <div>
            <label for="login">Login</label>
            <input type="text" id="login" name="login" required="" placeholder="Coloque seu login" 
                   onblur="BuscarUsuariosPorNome()">
        </div>
        <div>
            <label for="senha">Senha</label>
            <input type="password" required="" name="senha" id="senha" placeholder="Coloque sua senha">
        </div>
        <div>
            <label for="tipo">Tipo Usuário</label>
            <select name="tipo" id="tipo" tabindex="3">
                <option value="">Selecione</option>
            </select>
        </div>
        <div><button id="submit">Logar</button></div>
        </br>
        <div id="erro"></div>

        <script>
            $(document).ready(function () {
                console.log("entrei função");
                $("#submit").on("click", function () {
                    console.log("entrei click submit");
                    if ($('#login').val() === "") {
                        $("#login").focus();
                        $("#erro").html("<div>Por favor, preencher o campo usuário.</div>").show();
                        tempo();
                        return false;
                    }
                    if ($('#senha').val() === "") {
                        $("#senha").focus();
                        $("#erro").html("<div>Por favor, preencher o campo senha.</div>").show();
                        tempo();
                        return false;
                    }
                    $("#submit").prop("disabled", true);
                    $("#submit").html('<i class="fa fa-spinner" aria-hidden="true"></i> Aguarde...');

                    $.ajax({
                        type: 'post',
                        url: 'UsuarioLogar',
                        data: {
                            acao: "login",
                            login: $('#login').val(),
                            senha: $('#senha').val(),
                            tipo: $('#tipo').val()
                        },
                        success:
                                function (data) {
                                    if (data == 'ok') {
                                        window.location.href = "${pageContext.request.contextPath}/cadastros/homeLogado.jsp";
                                    } else {
                                        $('#submit').removeAttr('disabled');
                                        $("#submit").html('Entrar');
                        $("#wrapper_error").html("<div class='alert alert-danger'>Usuário ou senha incorreto.</div>").show();
                                        tempo();
                                    }
                                },
                        error:
                                function (data) {
                                    RefreshTable();
                                }
                    });
                });

                function tempo() {
                    setTimeout(function () {
                        $("#wrapper_error").hide();
                    }, 3000); // 3 segundos
                }
            });

            function BuscarUsuariosPorNome() 
            {
                usuario = '';
                console.log("entrei na function");
                $('#tipo').empty(); //..limpa select de tipo de usuario.
                loginUsuario = $('#login').val();
                console.log(loginUsuario);
                if (loginUsuario != 'null')
                {
                    console.log("vai rodar o ajax");
                    //console.log(idEst);
                    url = "UsuarioBuscarPorLogin?loginusuario="+loginUsuario;
                    //console.log(url);
                    $.getJSON(url, function (result) {
                        //alert(result);
                        $.each(result, function (index, value) {
                            $('#tipo').append(
                                    '<option id="usuario_' + value.idUsuario 
                                    + '"value="' + value.tipo + '">' 
                                    + value.tipo + '</option>'
                                    );
                            if(usuario !== ''){
                                $('#usuario_'+usuario).prop({selected: true});
                            }else{
                                $('#usuario_').prop({selected: true});
                            }

                        });
                        console.log("montou o select");
                    }
                    ).fail(function (obj, textStatus, error) {
                        alert('Erro do servidor: ' + textStatus + ', ' + error);
                    });
                }
            }
        </script>
   
<jsp:include page="footer.jsp"/>
