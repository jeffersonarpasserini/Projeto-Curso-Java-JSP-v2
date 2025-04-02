//-------------TRATAMENTO CAMPO CPFCNPJPESSOA--------------------------
//altera mascara quanto cpf/cnpj ganha focus
$(document).ready(function(){
    console.log('Ativo evento focus campo cpf');
    $('#cpfcnpjpessoa').focus(function(){
        trocaMascaraCpfCnpj("A");
    });
});

//Valida se cpf ou cnpj ja existe no banco de dados
//na saida do focus do campo (Evento Blur)
$(document).ready(function(){
    console.log('Ativo evento blur campo cpf');
    $('#cpfcnpjpessoa').blur(function(){
       
       var cpfCnpjLimpo = $('#cpfcnpjpessoa').unmask().val();
       console.log("CPF/CNPJ Limpo:");
       console.log(cpfCnpjLimpo);
       
       if (!validarCpfCnpj(cpfCnpjLimpo)){
            Swal.fire({
                position: 'center',
                icon: 'error',
                title: 'Verifique o CPF/CNPJ!',
                showConfirmButton: true,
                timer: 10000
            });
        }else{
           carregarPessoa($('#cpfcnpjpessoa').unmask().val());
           trocaMascaraCpfCnpj($('#cpfcnpjpessoa').val());
       }
    });
});

//alterar a mascara do cpf/cnpj de acordo com o
//tamanho da informação
function trocaMascaraCpfCnpj(cpfCnpj)
{
    console.log("entrei no troca mascara");
    if (cpfCnpj !== "A")
    {
        var masks = ['999.999.999-99', '99.999.999/9999-99'];
        var cpfcnpj = $('#cpfcnpjpessoa').unmask().val();
        mask = (cpfcnpj.length>11) ? masks[1] : masks[0];
        console.log("trocou: "+mask);
        $('#cpfcnpjpessoa').mask(mask);
    }
    else{
       console.log("limpou mascara"); 
       $('#cpfcnpjpessoa').unmask();
    }
        
};


//---------------------------VALIDAÇÕES--------------------------------
//Validação CNPJ/CPF
function validarCpfCnpj(cpfCnpj){
    console.log("entrei no validar cpf cnpj");
    if(cpfCnpj.length === 14){
        if (cnpjValidation(cpfCnpj)){
            console.log("Retorno CNPJ valido");
            return true;
        }else{
            console.log("Retorno CNPJ invalido");
            return false;
        }
    }else{
        if (validarCPF(cpfCnpj))
            return true;
        else
            return false;
    }
}

//Validação Cnpj
function cnpjValidation(value) {
    console.log("Entrei cnpjValidation -->"+value);
  if (!value) return false;

  // Aceita receber o valor como string, número ou array com todos os dígitos
  const isString = typeof value === 'string';
  const validTypes = isString || Number.isInteger(value) || Array.isArray(value);

  // Elimina valor em formato inválido
  if (!validTypes) return false;

  // Filtro inicial para entradas do tipo string
  if (isString) {
    // Limita ao máximo de 18 caracteres, para CNPJ formatado
    if (value.length > 18) return false;

    // Teste Regex para veificar se é uma string apenas dígitos válida
    const digitsOnly = /^\d{14}$/.test(value);
    // Teste Regex para verificar se é uma string formatada válida
    const validFormat = /^\d{2}.\d{3}.\d{3}\/\d{4}-\d{2}$/.test(value);

    // Se o formato é válido, usa um truque para seguir o fluxo da validação
    if (digitsOnly || validFormat) true;
    // Se não, retorna inválido
    else return false;
  }

  // Guarda um array com todos os dígitos do valor
  const match = value.toString().match(/\d/g);
  const numbers = Array.isArray(match) ? match.map(Number) : [];

  // Valida a quantidade de dígitos
  if (numbers.length !== 14) return false;
  
  // Elimina inválidos com todos os dígitos iguais
  const items = [...new Set(numbers)];
  if (items.length === 1) return false;

  // Cálculo validador
  const calc = (x) => {
    const slice = numbers.slice(0, x);
    let factor = x - 7;
    let sum = 0;

    for (let i = x; i >= 1; i--) {
      const n = slice[x - i];
      sum += n * factor--;
      if (factor < 2) factor = 9;
    }

    const result = 11 - (sum % 11);

    return result > 9 ? 0 : result;
  };

  // Separa os 2 últimos dígitos de verificadores
  const digits = numbers.slice(12);
  
  // Valida 1o. dígito verificador
  const digit0 = calc(12);
  if (digit0 !== digits[0]) return false;

  // Valida 2o. dígito verificador
  const digit1 = calc(13);
  return digit1 === digits[1];
}

function validarCNPJ(cnpj) {
 
    cnpj = cnpj.replace(/[^\d]+/g,'');
    if(cnpj === '') return false;
    if (cnpj.length !== 14)
        return false;
 
    // Elimina CNPJs invalidos conhecidos
    if (cnpj === "00000000000000" || 
        cnpj === "11111111111111" || 
        cnpj === "22222222222222" || 
        cnpj === "33333333333333" || 
        cnpj === "44444444444444" || 
        cnpj === "55555555555555" || 
        cnpj === "66666666666666" || 
        cnpj === "77777777777777" || 
        cnpj === "88888888888888" || 
        cnpj === "99999999999999")
        return false;
         
    // Valida DVs
    tamanho = cnpj.length - 2;
    numeros = cnpj.substring(0,tamanho);
    digitos = cnpj.substring(tamanho);
    soma = 0;
    pos = tamanho - 7;
    for (i = tamanho; i >= 1; i--) {
      soma += numeros.charAt(tamanho - i) * pos--;
      if (pos < 2)
            pos = 9;
    }
    resultado = soma % 11 < 2 ? 0 : 11 - soma % 11;
    if (resultado !== digitos.charAt(0))
        return false;
         
    tamanho = tamanho + 1;
    numeros = cnpj.substring(0,tamanho);
    soma = 0;
    pos = tamanho - 7;
    for (i = tamanho; i >= 1; i--) {
      soma += numeros.charAt(tamanho - i) * pos--;
      if (pos < 2)
            pos = 9;
    }
    resultado = soma % 11 < 2 ? 0 : 11 - soma % 11;
    if (resultado !== digitos.charAt(1))
          return false;
           
    return true;
}
//Validação CPF
function validarCPF(cpf) {	
	cpf = cpf.replace(/[^\d]+/g,'');	
	if(cpf === '') return false;	
	// Elimina CPFs invalidos conhecidos	
	if (cpf.length !== 11 || 
		cpf === "00000000000" || 
		cpf === "11111111111" || 
		cpf === "22222222222" || 
		cpf === "33333333333" || 
		cpf === "44444444444" || 
		cpf === "55555555555" || 
		cpf === "66666666666" || 
		cpf === "77777777777" || 
		cpf === "88888888888" || 
		cpf === "99999999999")
			return false;		
	// Valida 1o digito	
	add = 0;	
	for (i=0; i < 9; i ++)		
		add += parseInt(cpf.charAt(i)) * (10 - i);	
		rev = 11 - (add % 11);	
		if (rev === 10 || rev === 11)		
			rev = 0;	
		if (rev !== parseInt(cpf.charAt(9)))		
			return false;		
	// Valida 2o digito	
	add = 0;	
	for (i = 0; i < 10; i ++)		
		add += parseInt(cpf.charAt(i)) * (11 - i);	
	rev = 11 - (add % 11);	
	if (rev === 10 || rev === 11)	
		rev = 0;	
	if (rev !== parseInt(cpf.charAt(10)))
		return false;		
	return true;   
}




